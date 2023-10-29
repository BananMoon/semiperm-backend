package com.project.semipermbackend.post.dto;

import com.project.semipermbackend.domain.code.PostCategory;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

public interface PostUpdate {
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
    }
}
