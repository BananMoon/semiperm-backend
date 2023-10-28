package com.project.semipermbackend.post.service;

import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.comment.dto.CommentFindDto;
import com.project.semipermbackend.comment.service.CommentService;
import com.project.semipermbackend.common.dto.Pagination;
import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.exception.EntityNotFoundException;
import com.project.semipermbackend.common.error.exception.InappropriatePermissionException;
import com.project.semipermbackend.common.utils.PaginationUtil;
import com.project.semipermbackend.domain.code.PostCategory;
import com.project.semipermbackend.domain.code.PostSorting;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import com.project.semipermbackend.domain.post.Post;
import com.project.semipermbackend.domain.post.PostRepository;
import com.project.semipermbackend.post.dto.PostCreation;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.member.service.MemberService;
import com.project.semipermbackend.post.dto.PostUpdate;
import com.project.semipermbackend.post.dto.PostFindDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final MemberService memberService;
    private final CommentService commentService;
    private final PostRepository postRepository;

    // 게시글 생성
    @Transactional
    public PostCreation.ResponseDto create(Long memberId, PostCreation.RequestDto postCreation) {
        Member member = memberService.getMemberByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        Post savedPost = postRepository.save(postCreation.toEntity(member));
        return new PostCreation.ResponseDto(savedPost.getPostId());
    }

    // 게시글 상세 조회
    @Transactional
    public PostFindDto.Response getOne(Long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_POST, postId));

        // 경과 시각
        long elapsedUploadTimeSecondUnit = getUploadElapsedTimeSecUnit(post.getCreatedDate());

        Page<CommentFindDto.Response> commentsDto = commentService.getComments(0, 10, post);

        Pagination<CommentFindDto.Response> responsePagination = PaginationUtil.pageToPagination(commentsDto);

        // 조회수 증가
        post.addViewCount();

        return PostFindDto.Response.fromOne(post, elapsedUploadTimeSecondUnit, responsePagination);
    }

    private long getUploadElapsedTimeSecUnit(LocalDateTime uploadedDate) {
        Duration betweenTime = Duration.between(uploadedDate, LocalDateTime.now());
        return betweenTime.getSeconds();
    }

    /** 게시글 전체 조회 (최신순/인기순/정확순, 카테고리 필터링)
     * 1차에서는 카테고리 필터링하지 않는다.
     * 최신순 : order by createdDate desc
     * 좋아요순 : order by LikeCount desc
     * 인기순 : order by LikeCount, 댓글수 desc
     */
    public Page<PostFindDto.Response> getAll(int page, int perSize, SurgeryCategory filteredSurgeryCategory, PostCategory filteredPostCategory,
                                             PostSorting sorting) {

        Pageable pageable = PageRequest.of(page, perSize);

        return postRepository.findAllByFilteringOrderByDefaultCreatedDateDesc(pageable, filteredSurgeryCategory, filteredPostCategory, sorting)
                .map((post) -> PostFindDto.Response.fromSome(post, getUploadElapsedTimeSecUnit(post.getCreatedDate())));

    }

    /**
     * 게시글 좋아요
      */
    @Transactional
    public void likeOne(Long postId) {
        // 게시글 조회
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_POST, postId));

        post.addLike();
    }

    @Transactional
    public void updateOne(Long postId, PostUpdate.RequestDto requestDto) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_POST, postId));

        post.update(requestDto);
    }
    // 내용, 제목
    public Page<PostFindDto.Response> searchKeyword(int page, int perSize, String keyword, PostSorting sorting) {
        Pageable pageable = PageRequest.of(page, perSize);

        return postRepository.findAllByTitleOrContentIsContainsIgnoreCaseOrderBySorting(pageable, keyword, sorting)
                .map((post) -> PostFindDto.Response.fromSome(post, getUploadElapsedTimeSecUnit(post.getCreatedDate())));
    }

    public Page<PostFindDto.Response> findMyPosts(Integer page, Integer perSize, Long memberId) {
        Member member = memberService.getMemberByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        Pageable pageable = PageRequest.of(page, perSize);

        return postRepository.findAllByMember(pageable, member)
                .map(PostFindDto.Response::forMyPage);
    }

    @Transactional
    public void deleteOne(Long postId) {
        Long memberId = JwtTokenProvider.getMemberIdFromContext();

        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_POST, postId));
        if (!memberId.equals(post.getMember().getMemberId())) {
            throw new InappropriatePermissionException();
        }
        postRepository.deleteByPostId(postId);

    }
}


