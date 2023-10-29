package com.project.semipermbackend.comment.dto;

import lombok.Builder;
import lombok.Data;

public interface CommentUpdateDto {
    @Builder
    @Data
    class Request {
        Long commentId;
        String content;
    }
}
