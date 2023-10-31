package com.project.semipermbackend.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.semipermbackend.comment.dto.CommentFindDto;
import com.project.semipermbackend.common.dto.Pagination;
import com.project.semipermbackend.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface PostFindDto {

    // 회원 닉네임, 좋아요 갯수, 현재 시각 기준 작성 시각, 조회수, 타이틀, 내용, List<댓글>
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Response {

        private Long postId;
        @NotNull
        private String nickname;

        private int likeCount;
        private long elapsedUploadTimeSecondUnit;    // 경과 업로드 시각

        private int viewCount;
        @NotNull
        private String title;
        @NotNull
        private String content;
        private String surgeryCategory;    // 타입 대신 code 값으로.
        private String postCategory;

        Pagination<CommentFindDto.Response> comments;
        private int commentCount;

        private LocalDateTime createdDate;

        // 상세 조회 (단일)
        public static Response fromOne (Post post, long uploadElaspedTimeDayUnit, Pagination<CommentFindDto.Response> responsePagination) {
            return Response.builder()
                    .postId(post.getPostId())
                    .nickname(post.getMember().getNickname())
                    .likeCount(post.getLikeCount())
                    .viewCount(post.getViewCount())
                    .elapsedUploadTimeSecondUnit(uploadElaspedTimeDayUnit)
                    .title(post.getTitle())
                    .content(post.getContent())
                    .comments(responsePagination)
                    .build();
        }

        // 다수 조회
        public static Response fromSome(Post post, long uploadElaspedTimeDayUnit) {
            return Response.builder()
                    .postId(post.getPostId())
                    .nickname(post.getMember().getNickname())
                    .title(post.getTitle())
                    .content(subString(post.getContent())) // 100자
                    .likeCount(post.getLikeCount())
                    .viewCount(post.getViewCount())
                    .elapsedUploadTimeSecondUnit(uploadElaspedTimeDayUnit)
                    .surgeryCategory(post.getSurgeryCategory().getCode())
                    .postCategory(post.getPostCategory().getCode())
                    .build();
        }

        private static String subString(String content) {
            int viewLimitLength = 100;

            if (content.length() < viewLimitLength) {
                return content;
            }
            return content.substring(0, viewLimitLength);
        }

        public static Response forMyPage(Post post) {
            return Response.builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .content(subString(post.getContent())) // 100자
                    .createdDate(post.getCreatedDate())
                    .build();
        }

    }
}
