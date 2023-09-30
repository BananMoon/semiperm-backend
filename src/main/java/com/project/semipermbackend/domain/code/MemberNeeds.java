package com.project.semipermbackend.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.project.semipermbackend.common.error.exception.InvalidRequestDataException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum MemberNeeds {
    CLOSED("내 위치에서 가까운 샵의 정보가 필요해요")
    , NAMED_EFFICIENT("유명하고 실력있는 샵에 가고싶어요")
    ,TRUTH_REVIEW("객관적이고 사실적인 리뷰를 찾고 싶어요")
    ,INFO_EXCHANGE("같은 고민을 공유하는 사람들과의 정보 교류를 원해요")
    ;

    private final String message;

    @JsonCreator
    public static MemberNeeds inputStrToEnum(String input) {
        return Stream.of(MemberNeeds.values())
                .filter(needs -> needs.name().equals(input))
                .findFirst()
                .orElseThrow(InvalidRequestDataException::new);
    }
}