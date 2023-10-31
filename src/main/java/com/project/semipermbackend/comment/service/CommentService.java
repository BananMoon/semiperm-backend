package com.project.semipermbackend.comment.service;

import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.comment.dto.CommentCreationDto;
import com.project.semipermbackend.comment.dto.CommentFindDto;
import com.project.semipermbackend.comment.dto.CommentUpdateDto;
import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.exception.EntityNotFoundException;
import com.project.semipermbackend.common.error.exception.InappropriatePermissionException;
import com.project.semipermbackend.domain.comment.Comment;
import com.project.semipermbackend.domain.comment.CommentGroupNoMapping;
import com.project.semipermbackend.domain.comment.CommentRepository;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.domain.post.Post;
import com.project.semipermbackend.domain.post.PostRepository;
import com.project.semipermbackend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberService memberService;
    @Transactional
    public CommentCreationDto.Response create(Long memberId, Long postId, CommentCreationDto.Request commentCreation) {
        // 1. 회원 조회
        Member member = memberService.getMemberByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        // 2. 게시글 조회
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_POST, postId));

        // 3. 댓글 생성
        Comment newComment = commentCreation.toEntity(member, post);

        // 3.1 update groupNo
        // parentId==0: 부모이므로 새 groupNo 사용 (parentId!=0: 자식은 dto 내 부모의 groupNo 사용)
        if (isParentComment(commentCreation)) {
            CommentGroupNoMapping groupNo = commentRepository.findTopByPostOrderByGroupNoDesc(post);
            if (Objects.isNull(groupNo) || Objects.isNull(groupNo.getGroupNo())) {
                newComment.setGroupNo(0L);
            } else {
                newComment.setGroupNo(groupNo.getGroupNo() + 1);
            }
        }

        // 3.2 저장
        Comment createdComment = commentRepository.save(newComment);

        // 4. 게시글 댓글 갯수 업데이트
        createdComment.getPost().addComment(createdComment);

        return new CommentCreationDto.Response(createdComment.getCommentId());
    }

    private boolean isParentComment(CommentCreationDto.Request commentCreation) {
        return commentCreation.getParentId() == 0;
    }

    /**
     * 게시글 상세 조회 시에만 호출된다.
     */
    public Page<CommentFindDto.Response> getComments(int page,
                                                     int pagePerSize,
                                                     Post post) {
        Pageable pageable = PageRequest.of(page, pagePerSize);

        return commentRepository.findAllByPostOrderByGroupNoAscCreatedDateAsc(pageable, post)
                .map(CommentFindDto.Response::from);
    }

    @Transactional
    public void likeOne(Long commentId) {
        // 코멘트 조회
        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_COMMENT, commentId));

        comment.addLike();
    }

    /**
     * 내가 쓴 댓글 조회
     */
    public Page<CommentFindDto.Response> findMyComments(int page, int perSize, Long memberId) {
        Member member = memberService.getMemberByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_COMMENT));

        Pageable pageable = PageRequest.of(page, perSize);

        return commentRepository.findAllByMember(pageable, member)
                .map(CommentFindDto.Response::forMyPage);
    }

    // 수정
    @Transactional
    public void updateOne(Long commentId, CommentUpdateDto.Request requestDto) {
        Long memberId = JwtTokenProvider.getMemberIdFromContext();
        Member loginedUser = memberService.getMemberByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_COMMENT, commentId));

        if (!loginedUser.getMemberId().equals(comment.getMember().getMemberId())) {
            throw new InappropriatePermissionException();
        }
        comment.update(requestDto);
    }

    @Transactional
    public void deleteOne(Long commentId) {
        Long memberId = JwtTokenProvider.getMemberIdFromContext();
        Member member = memberService.getMemberByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        Comment comment = commentRepository.findByCommentIdAndMember(commentId, member)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_COMMENT, commentId));

        commentRepository.delete(comment);
    }
}
