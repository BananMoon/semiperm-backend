package com.project.semipermbackend.common.utils;

import com.project.semipermbackend.domain.code.PostCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class PostCategoryConverter implements AttributeConverter<PostCategory, String> {

    @Override
    public String convertToDatabaseColumn(PostCategory attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.getTitle();
    }

    @Override
    public PostCategory convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) {
            return null;
        }
        for (PostCategory code : PostCategory.values()) {
            if (code.getTitle().equals(dbData)) {
                return code;
            }
        }
        // TODO Error Handling
        return null;
    }
}
