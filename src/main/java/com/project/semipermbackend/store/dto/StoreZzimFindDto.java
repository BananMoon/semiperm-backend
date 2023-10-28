package com.project.semipermbackend.store.dto;

import com.project.semipermbackend.domain.store.MemberZzimStore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreZzimFindDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long memberZzimStoreId;
        private String encodedPlaceId;
        private float totalReviewRating;

        public static Response from(MemberZzimStore zzimStore) {
            return new Response(zzimStore.getMemberZzimStoreId(),
                    zzimStore.getStore().getEncodedPlaceId(),
                    zzimStore.getStore().getTotalReviewRating());
        }
        // 태그값 들어와야할듯  ex) 후기많은, 신뢰도 높은
    }
}