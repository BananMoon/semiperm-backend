package com.project.semipermbackend.common.error.exception;

import com.project.semipermbackend.common.error.ErrorCode;

public class EntityAlreadyExistsException extends BusinessException {

    public EntityAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
