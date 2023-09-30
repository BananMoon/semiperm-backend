package com.project.semipermbackend.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.project.semipermbackend.common.code.EnumMapperType;
import com.project.semipermbackend.common.error.exception.InvalidRequestDataException;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum SurgeryCategory implements EnumMapperType {
    SMP("SMP두피"),
    HAIRLINE("헤어라인"),
    EYEBROW_TATOO("눈썹문신"),
    EYELINE("아이라인"),
    LIPS_TATTO("입술문신"),
    SCAR_COVERUP("흉터 커버업"),
    SEMIPERM_REMOVAL("반영구 제거"),

    ALL("전체")
    ;

    private final String title;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @JsonCreator
    public static SurgeryCategory inputStrToEnum(String input) {
        return Stream.of(SurgeryCategory.values())
                .filter(category -> category.name().equals(input))
                .findFirst()
                .orElseThrow(InvalidRequestDataException::new);
    }
}
