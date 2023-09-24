package com.project.semipermbackend.domain.code;

import com.project.semipermbackend.common.code.EnumMapperType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostSorting implements EnumMapperType {
    POPULARITY("인기순"),
    LATEST("최신순")
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
}
