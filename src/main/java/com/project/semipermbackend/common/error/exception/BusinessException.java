package com.project.semipermbackend.common.error.exception;

import com.project.semipermbackend.common.error.ErrorCode;
import lombok.Getter;

public class BusinessException extends RuntimeException{
    @Getter
    private final ErrorCode errorCode;

    protected BusinessException(ErrorCode errorCode) {
        super(errorCode.name() + " : " + errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
