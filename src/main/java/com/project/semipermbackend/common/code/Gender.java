package com.project.semipermbackend.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum Gender implements EnumMapperType{
    WOMAN("W")
    , MAN ("M")
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
     * request로 들어온 String 값을 Enum으로 변환한다.
     */
    @JsonCreator
    public static Gender inputStrToEnum(String input) {
        System.out.println("=============" + input);
        return Stream.of(Gender.values())
                .filter(gender -> gender.title.equals(input.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
