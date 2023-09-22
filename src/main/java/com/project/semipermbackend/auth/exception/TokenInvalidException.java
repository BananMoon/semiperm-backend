package com.project.semipermbackend.auth.exception;

import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.exception.BusinessException;

public class TokenInvalidException extends BusinessException {

    public TokenInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
