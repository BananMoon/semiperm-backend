package com.project.semipermbackend.member.exception;

import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.exception.BusinessException;

public class UnauthenticatedUserException extends BusinessException {
    public UnauthenticatedUserException() {
        super(ErrorCode.NEED_SONAIL_AUTH_INFO);
    }
}
