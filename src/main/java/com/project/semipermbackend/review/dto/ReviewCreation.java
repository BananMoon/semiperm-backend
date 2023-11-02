package com.project.semipermbackend.review.dto;

import com.project.semipermbackend.domain.code.ReviewCategory;
import lombok.Data;

public interface ReviewCreation {
    @Data
    class RequestDto {
        String placeId;
        float reviewRating;
        ReviewCategory reviewCategory;
        String content;
    }
}
