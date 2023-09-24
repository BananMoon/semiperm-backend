package com.project.semipermbackend.post.service;

import com.project.semipermbackend.comment.dto.CommentFindDto;
import com.project.semipermbackend.comment.service.CommentService;
import com.project.semipermbackend.common.dto.Pagination;
import com.project.semipermbackend.common.utils.PaginationUtil;
import com.project.semipermbackend.domain.post.Post;
import com.project.semipermbackend.domain.post.PostRepository;
import com.project.semipermbackend.post.dto.PostCreation;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.member.service.MemberService;
import com.project.semipermbackend.post.dto.PostViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    private final static int ONE_DAY_MIN_UNIT = 60*24;

    // Member : post = 1:N
    @Transactional
    public PostCreation.ResponseDto create(Long memberId, PostCreation.RequestDto postCreation) {
        // 회원 조회
        Member member = memberService.getMemberByMemberId(memberId);

        // 게시글 생성
        Post savedPost = postRepository.save(postCreation.toEntity(member));
        return new PostCreation.ResponseDto(savedPost.getPostId());
    }

    // 게시글 상세 조회
    public PostViewDto.Response getOne(Long postId) {
        Post post = postRepository.findByPostId(postId);

        // 경과 시각
        Duration betweenTime = Duration.between(post.getCreatedDate(), LocalDateTime.now());
        int elaspedUploadTimeDayUnit = 0;
        if (betweenTime.getSeconds() > ONE_DAY_MIN_UNIT) {
            elaspedUploadTimeDayUnit = (int)(betweenTime.getSeconds() / 24); // 48시간 -> 2일 전, 47시간 -> 1일전
        }

        Page<CommentFindDto.Response> commentsDto = commentService.getComments(1, 10, post);

        Pagination<CommentFindDto.Response> responsePagination = PaginationUtil.pageToPagination(commentsDto);
        return PostViewDto.Response.from(post, elaspedUploadTimeDayUnit, responsePagination);
    }

    // 게시글 전체 조회

}
