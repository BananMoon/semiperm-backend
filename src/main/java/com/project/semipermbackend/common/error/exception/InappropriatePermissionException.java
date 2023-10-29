package com.project.semipermbackend.common.error.exception;

import com.project.semipermbackend.common.error.ErrorCode;

public class InappropriatePermissionException extends BusinessException {
    public InappropriatePermissionException() {
        super(ErrorCode.INAPPROPRIATE_PERMISSION);
    }

}
