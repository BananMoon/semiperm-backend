package com.project.semipermbackend.comment.dto;

import com.project.semipermbackend.domain.comment.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface CommentFindDto {
    @Builder
    @Data
    class Response {
        private Long commentId;
        private Long parentId;

        private String content;

        private Long postId;

//        private LocalDateTime createdAt;
        private String nickname;

        private int likeCount;


        public static Response from(Comment comment) {
            return Response.builder()
                        .commentId(comment.getCommentId())
                        .parentId(comment.getParentId())
                        .content(comment.getContent())
                        .postId(comment.getPost().getPostId())
                        .nickname(comment.getMember().getNickname())
                        .likeCount(comment.getLikeCount())
                        .build();
        }
    }

}
