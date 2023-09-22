package com.project.semipermbackend.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResultResponseDto {
    private String code;
    private String message;

    public static ErrorResultResponseDto of(ErrorCode errorCode, String msg) {
        return new ErrorResultResponseDto(errorCode.name(), msg);
    }
}
