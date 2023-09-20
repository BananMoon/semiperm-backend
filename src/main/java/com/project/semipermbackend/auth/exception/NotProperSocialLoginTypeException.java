package com.project.semipermbackend.auth.exception;

import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.exception.BusinessException;

public class NotProperSocialLoginTypeException extends BusinessException {

    public NotProperSocialLoginTypeException() {

        super(ErrorCode.NOT_PROPER_SOCIALLOGIN_TYPE);
    }
}
