package com.project.semipermbackend.comment.service;

import com.project.semipermbackend.comment.dto.CommentCreationDto;
import com.project.semipermbackend.comment.dto.CommentFindDto;
import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.exception.EntityNotFoundException;
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
    private final MemberService memberService;
    private final PostRepository postRepository;
    @Transactional
    public CommentCreationDto.Response create(Long memberId, Long postId, CommentCreationDto.Request commentCreation) {
        // 1. 회원 조회
        Member member = memberService.getMemberByMemberId(memberId);

        // 2. 게시글 조회
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_POST, postId));

        // 3. 댓글 생성
        Comment convertedComment = commentCreation.toEntity(member, post);

        // 3.1 groupNo 지정
        // TODO QueryDsl로 해결
        CommentGroupNoMapping groupNo = commentRepository.findTopByPostOrderByGroupNoDesc(post);
        if (Objects.isNull(groupNo.getGroupNo())) {
            convertedComment.setGroupNo(0L);
        } else {
            convertedComment.setGroupNo(groupNo.getGroupNo() + 1);
        }
        // 3.2 저장
        Comment createdComment = commentRepository.save(convertedComment);

        // 4. 게시글 댓글 갯수 업데이트
        createdComment.getPost().addComment();

        return new CommentCreationDto.Response(createdComment.getCommentId());
    }

    /**
     * 게시글 상세 조회 시에만 호출된다.
     */
    public Page<CommentFindDto.Response> getComments(int page,
                                                     int pagePerSize,
                                                     Post post) {
        Pageable pageable = PageRequest.of(page-1, pagePerSize);

        return commentRepository.findPageByPostOrderByCreatedDateAsc(pageable, post)
                .map(CommentFindDto.Response::from);
    }

    @Transactional
    public void likeOne(Long commentId) {
        // 코멘트 조회
        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_COMMENT, commentId));

        comment.addLike();
    }
}
