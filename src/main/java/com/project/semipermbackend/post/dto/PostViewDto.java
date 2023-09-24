package com.project.semipermbackend.post.dto;

import com.project.semipermbackend.comment.dto.CommentFindDto;
import com.project.semipermbackend.common.dto.Pagination;
import com.project.semipermbackend.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public interface PostViewDto {

    // 회원 닉네임, 좋아요 갯수, 현재 시각 기준 작성 시각, 조회수, 타이틀, 내용, List<댓글>
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    class Response {
        @NotNull
        private String nickname;

        private int likeCount;
        private int elapsedUploadTime;    // 경과 업로드 시각

        private int viewCount;
        @NotNull
        private String title;
        @NotNull
        private String content;

        Pagination<CommentFindDto.Response> comments;

        public static Response from(Post post, int elaspedUploadTimeDayUnit, Pagination<CommentFindDto.Response> responsePagination) {
            return PostViewDto.Response.builder()
                    .nickname(post.getMember().getNickname())
                    .likeCount(post.getLikeCount())
                    .elapsedUploadTime(elaspedUploadTimeDayUnit)
                    .title(post.getTitle())
                    .content(post.getContent())
                    .comments(responsePagination)
                    .build();
        }
    }
}
