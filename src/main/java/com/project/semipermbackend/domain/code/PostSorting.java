package com.project.semipermbackend.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.project.semipermbackend.common.code.EnumMapperType;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum PostSorting implements EnumMapperType {
    POPULARITY("인기순"),
    LATEST("최신순"),
    LIKE("좋아요순")
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
    public static PostSorting inputStrToEnum(String input) {
        return Stream.of(PostSorting.values())
                .filter(sorting -> sorting.name().equals(input))
                .findFirst()
                .orElse(null);
    }
}
