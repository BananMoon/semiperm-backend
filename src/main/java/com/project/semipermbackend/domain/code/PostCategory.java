package com.project.semipermbackend.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.project.semipermbackend.common.code.EnumMapperType;
import com.project.semipermbackend.common.code.Gender;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum PostCategory implements EnumMapperType {
    TOTAL("전체보기")
    , WORRY("고민")
    , INFO("정보")
    , FREE("자유게시글");

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
    public static PostCategory inputStrToEnum(String input) {
        return Stream.of(PostCategory.values())
                .filter(category -> category.title.equals(input))
                .findFirst()
                .orElse(null);
    }
}
