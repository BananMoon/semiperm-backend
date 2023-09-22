package com.project.semipermbackend.common.code;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface EnumMapperType {
    String getCode();

    String getTitle();

}
