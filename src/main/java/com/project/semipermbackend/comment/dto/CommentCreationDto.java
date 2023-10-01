package com.project.semipermbackend.comment.dto;

import com.project.semipermbackend.domain.comment.Comment;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface CommentCreationDto {
    @Data
    class Request {
        @Size(max = 255)
        @NotNull
        private String content;
        private Long parentId;  // TODO parent이면 0, parent 아래 자식 댓글이면 parentId 전달 가능?

        public Comment toEntity(Member member, Post post) {
            return Comment.builder()
                    .parentId(parentId)
                    .content(content)
                    .post(post)
                    .member(member)
                    .build();
        }
    }
    @AllArgsConstructor
    class Response {
        private Long commentId;
    }

}
