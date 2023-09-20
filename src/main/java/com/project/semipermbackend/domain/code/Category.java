package com.project.semipermbackend.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.project.semipermbackend.common.code.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Category implements EnumMapperType {
    SMP("SMP두피"),
    HAIRLINE("헤어라인"),
    EYEBROW_TATOO("눈썹문신"),
    EYELINE("아이라인"),
    LIPS_TATTO("입술문신"),
    SCAR_COVERUP("흉터 커버업"),
    SEMIPERM_REMOVAL("반영구 제거");
    ;

    private final String title;

    @Override
    public String getCode() {
        return name();
    }

    @JsonCreator
    public static Category inputStrToEnum(String input) {
        return Stream.of(Category.values())
                .filter(category -> category.name().equals(input))
                .findFirst()
                .orElse(null);
    }
}
