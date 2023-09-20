package com.project.semipermbackend.common.error;

import com.project.semipermbackend.common.dto.ApiResultDto;
import com.project.semipermbackend.common.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResultDto<Void>> handleBusinessException(BusinessException ex) {
        log.error("Exception trace : ", ex);

        ErrorCode errorCode = ex.getErrorCode();
        ErrorResultResponseDto errorResponse = new ErrorResultResponseDto(errorCode.name(), errorCode.getMessage());
        ApiResultDto<Void> resultDto = ApiResultDto.fail(errorResponse);
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(resultDto);
    }
}
