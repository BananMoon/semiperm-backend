package com.project.semipermbackend.common.utils;

import com.project.semipermbackend.domain.code.SurgeryCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;
@Converter
public class SurgeryCategoryConverter implements AttributeConverter<SurgeryCategory, String> {
    @Override
    public String convertToDatabaseColumn(SurgeryCategory attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.getTitle();
    }

    @Override
    public SurgeryCategory convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) {
            return null;
        }
        for (SurgeryCategory code : SurgeryCategory.values()) {
            if (code.getTitle().equals(dbData)) {
                return code;
            }
        }
        // TODO Error Handling
        return null;
    }
}
