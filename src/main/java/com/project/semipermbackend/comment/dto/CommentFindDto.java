package com.project.semipermbackend.comment.dto;

import com.project.semipermbackend.domain.comment.Comment;
import lombok.Builder;
import lombok.Data;

public interface CommentFindDto {
    @Builder
    @Data
    class Response {
        private Long commentId;
        private Long parentId;

        private String content;

        private Long postId;

//        private LocalDateTime createdAt;  // TODO 필요 시 추가 예정
        private String nickname;

        private int likeCount;
        private Long groupNo;


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
    }

}
