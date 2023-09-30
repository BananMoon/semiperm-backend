package com.project.semipermbackend.common.utils;

import com.project.semipermbackend.common.code.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

/**
 * Entity 내 Enum 필드를 필드의 String 타입의 값으로 변환 후 저장한다.
 */
@Converter
public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        if (Objects.isNull(attribute)) {
            // TODO Error Handling
            return null;
        }
        return attribute.getTitle();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) {
        // TODO Error Handling
            return null;
        }
        for (Gender code : Gender.values()) {
            if (code.getTitle().equalsIgnoreCase(dbData)) {
                return code;
            }
        }
        return null;
    }
}
