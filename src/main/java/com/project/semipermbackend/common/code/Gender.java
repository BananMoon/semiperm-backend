package com.project.semipermbackend.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
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

    @JsonCreator
    public static Gender inputStrToEnum(String input) {
        return Stream.of(Gender.values())
                .filter(gender -> gender.getTitle().equals(input.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
