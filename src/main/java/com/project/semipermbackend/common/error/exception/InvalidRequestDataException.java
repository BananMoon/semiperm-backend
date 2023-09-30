package com.project.semipermbackend.common.error.exception;

import com.project.semipermbackend.common.error.ErrorCode;

public class InvalidRequestDataException extends BusinessException {
    public InvalidRequestDataException() {
        super(ErrorCode.INVALID_REQUEST_DATA);
    }

}
