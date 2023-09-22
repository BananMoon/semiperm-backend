package com.project.semipermbackend.common.utils;

import com.project.semipermbackend.common.code.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.getTitle();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) {
            return null;
        }
        for (Gender code : Gender.values()) {
            if (code.getTitle().equalsIgnoreCase(dbData)) {
                return code;
            }
        }
        // TODO Error Handling
        return null;
    }
}
