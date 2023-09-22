package com.project.semipermbackend.post.service;

import com.project.semipermbackend.domain.comment.Comment;
import com.project.semipermbackend.domain.post.Post;
import com.project.semipermbackend.domain.post.PostRepository;
import com.project.semipermbackend.post.dto.PostCreation;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.member.service.MemberService;
import com.project.semipermbackend.post.dto.PostViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final MemberService memberService;
//    private final CommentService commentService;
    private final PostRepository postRepository;

    private final static int ONE_DAY_MIN_UNIT = 60*24;

    // Member : post = 1:N
    @Transactional
    public PostCreation.ResponseDto create(Long memberId, PostCreation.RequestDto postCreation) {
        // 회원 조회
        Member member = memberService.getMember(memberId);

        // 게시글 생성
        Post savedPost = postRepository.save(postCreation.toEntity(member));
        return new PostCreation.ResponseDto(savedPost.getPostId());
    }

    // 게시글 단일 조회
    public PostViewDto.Response getOne(Long postId) {
        Post post = postRepository.findByPostId(postId);

        // 경과 시각
        Duration betweenTime = Duration.between(post.getCreatedDate(), LocalDateTime.now());
        int elaspedUploadTimeDayUnit = 0;
        if (betweenTime.getSeconds() > ONE_DAY_MIN_UNIT) {
            elaspedUploadTimeDayUnit = (int)(betweenTime.getSeconds() / 24); // 48시간 -> 2일 전, 47시간 -> 1일전
        }

        // TODO 연관 댓글 :  Pagination 필요
//        List<Comment> comments = commentService.getComments(post);
//        CommentViewDto.Response commentDto = CommentViewDto.Response.from(comments);

        return PostViewDto.Response.builder()
                .nickname(post.getMember().getNickname())
                .likeCount(post.getLikeCount())
                .elapsedUploadTime(elaspedUploadTimeDayUnit)
                .title(post.getTitle())
                .content(post.getContent())
//                .commentDto()
                .build();
    }

    // 게시글 전체 조회

}
