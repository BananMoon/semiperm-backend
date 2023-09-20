package com.project.semipermbackend.member.dto;

import com.project.semipermbackend.common.code.FlagYn;
import com.project.semipermbackend.common.code.Gender;
import com.project.semipermbackend.domain.code.Category;
import com.project.semipermbackend.domain.code.MemberNeeds;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Set;

// TODO 유효성 체크 애노테이션 추가
public interface MemberCreation {
    @Builder
    @Data
    class RequestDto {
        @NonNull
        Long accountId;
        @NonNull
        LocalDate birth;
        @NonNull
        Gender gender;

        Set<Category> interestingFields;
        Set<MemberNeeds> needInformations;

        // 약관 동의
        @NonNull
        FlagYn isOrderThan14;
        @NonNull
        FlagYn personalInfoServiceUsageAgreeYn;
        FlagYn agreeToADYn;
    }

    @AllArgsConstructor
    class ResponseDto {
        Long accountId;
    }
}
