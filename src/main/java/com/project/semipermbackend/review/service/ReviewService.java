package com.project.semipermbackend.review.service;

import com.project.semipermbackend.domain.review.Review;
import com.project.semipermbackend.domain.review.ReviewRepository;
import com.project.semipermbackend.domain.store.Store;
import com.project.semipermbackend.post.dto.PostCreation;
import com.project.semipermbackend.review.dto.ReviewCreation;
import com.project.semipermbackend.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final StoreService storeService;

    @Transactional
    public PostCreation.ResponseDto create(ReviewCreation.RequestDto requestDto) {
        // 기존 Store 없으면 create, 있으면 update
        Store store = storeService.createOrFindExistingStore(requestDto.getPlaceId());

        // 리뷰 엔티티 생성
        Review savedReview = reviewRepository.save(
                Review.builder()
                .content(requestDto.getContent())
                .reviewCategory(requestDto.getReviewCategory())
                .rating(requestDto.getReviewRating())
                .store(store)
                .build());
        store.addReview(savedReview);
        return new PostCreation.ResponseDto(savedReview.getId());
    }
}
