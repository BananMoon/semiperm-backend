package com.project.semipermbackend.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.project.semipermbackend.common.code.EnumMapperType;
import com.project.semipermbackend.common.error.exception.InvalidRequestDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public enum PostCategory implements EnumMapperType {
    WORRY("고민"),
    INFO("정보"),
    FREE("자유게시글"),
    TOTAL("전체보기")
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
    public static PostCategory inputStrToEnum(String input) {
        log.info("inputStrToEnum");
        return Stream.of(PostCategory.values())
                .filter(category -> category.name().equals(input))
                .findFirst()
                .orElseThrow(InvalidRequestDataException::new);
    }
}
