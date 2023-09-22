package com.project.semipermbackend.auth.exception;

import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.exception.BusinessException;

public class NotFoundException extends BusinessException {
    public NotFoundException() {
        super(ErrorCode.NOT_FOUND_MEMBER);
    }
}
