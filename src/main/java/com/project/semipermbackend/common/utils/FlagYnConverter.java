package com.project.semipermbackend.common.utils;

import com.project.semipermbackend.common.code.FlagYn;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class FlagYnConverter implements AttributeConverter<FlagYn, String> {
    /**
     *  Enum값을 데이터에 save 시 code 값으로 저장한다.
     * @param attribute  the entity attribute value to be converted
     */
    @Override
    public String convertToDatabaseColumn(FlagYn attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.getTitle();
    }

    /**
     *  데이터 값을 Enum 값으로 변환시킨다.
     * @param dbData  the data from the database column to be
     *                converted
     */
    @Override
    public FlagYn convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) {
            return null;
        }
        for (FlagYn code : FlagYn.values()) {
            if (code.getTitle().equals(dbData)) {
                return code;
            }
        }
        // TODO Error Handling
        return null;
    }
}
