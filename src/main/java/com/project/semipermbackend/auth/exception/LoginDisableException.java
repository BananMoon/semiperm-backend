package com.project.semipermbackend.auth.exception;

import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.exception.BusinessException;

public class LoginDisableException extends BusinessException {

    public LoginDisableException() {
        super(ErrorCode.LOGIN_DISABLE_STATUS);
    }

    public LoginDisableException(ErrorCode errorCode) {
        super(errorCode);
    }
}
