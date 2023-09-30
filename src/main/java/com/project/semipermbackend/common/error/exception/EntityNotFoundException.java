package com.project.semipermbackend.common.error.exception;

import com.project.semipermbackend.common.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
    public EntityNotFoundException(ErrorCode errorCode, Long relatedId) {
        super(errorCode, relatedId);
    }
}
