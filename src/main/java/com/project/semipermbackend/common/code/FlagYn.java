package com.project.semipermbackend.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum FlagYn implements EnumMapperType {
    YES("Y"),
    NO("N")
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

    /**
     * DTO로 변환 시, 요청 필드로 전달받은 title 값을 Enum값으로 변환시킨다.
     * @param input
     */
    @JsonCreator
    public static FlagYn inputStrToEnum(String input) {
        return Stream.of(FlagYn.values())
                .filter(flag -> flag.title.equals(input.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
