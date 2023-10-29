package com.project.semipermbackend.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.semipermbackend.domain.comment.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public interface CommentFindDto {
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Response {
        private Long commentId;
        private Long parentId;

        private String content;

        private Long postId;

        private String nickname;

        private int likeCount;
        private Long groupNo;

        // 마이페이지
        private LocalDateTime createdDate;  // TODO 필요없으면 삭제 예정
        private String title;

        public static Response from(Comment comment) {
            return Response.builder()
                        .commentId(comment.getCommentId())
                        .parentId(comment.getParentId())
                        .content(comment.getContent())
                        .postId(comment.getPost().getPostId())
                        .nickname(comment.getMember().getNickname())
                        .likeCount(comment.getLikeCount())
                        .groupNo(comment.getGroupNo())
                        .build();
        }
        public static Response forMyPage(Comment comment) {
            return Response.builder()
                    .commentId(comment.getCommentId())
                    .content(comment.getContent())
                    .createdDate(comment.getCreatedDate())
                    .title(comment.getPost().getTitle())
                    .build();

        }
    }

}
