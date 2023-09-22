package com.project.semipermbackend.post.dto;

import com.project.semipermbackend.domain.code.PostCategory;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.domain.post.Post;
import lombok.*;

public interface PostCreation {
    @Builder
    @Data
    class RequestDto {
        @NonNull
        PostCategory postCategory;
        @NonNull
        SurgeryCategory surgeryCategory;
        @NonNull
        String title;
        String content;

        public Post toEntity(Member member) {
            return Post.builder()
                    .member(member)
                    .postCategory(postCategory)
                    .surgeryCategory(surgeryCategory)
                    .title(title)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    class ResponseDto {
        Long postId;
    }
}
