package com.project.semipermbackend.comment.dto;

import com.project.semipermbackend.domain.comment.Comment;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface CommentCreationDto {
    @AllArgsConstructor
    @Data
    class Request {
        @Size(max = 255)
        @NotNull
        private String content;
        @NotNull
        private Long parentId;  // 부모댓글이면 0, 답글이면 부모댓글의 id
        @Nullable
        private Long groupNo;  // 부모댓글이면 null, 답글이면 부모댓글의 groupNo

        public Comment toEntity(Member member, Post post) {
            return Comment.builder()
                    .parentId(parentId)
                    .content(content)
                    .groupNo(groupNo)
                    .post(post)
                    .member(member)
                    .build();
        }
    }
    @AllArgsConstructor
    @Getter
    class Response {
        private Long commentId;
    }

}
