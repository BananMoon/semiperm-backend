package com.project.semipermbackend.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.semipermbackend.common.error.ErrorResultResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiResultDto<T> {
    private boolean isSuccess;
    private T data;
    private ErrorResultResponseDto error;

    public static ApiResultDto success() {
        return ApiResultDto.builder()
                .isSuccess(true)
                .build();
    }

    public static <R> ApiResultDto<R> success(R data) {
        return ApiResultDto.<R>builder()
                .isSuccess(true)
                .data(data)
                .build();
    }

    public static ApiResultDto fail(ErrorResultResponseDto error) {
        return ApiResultDto.builder()
                .isSuccess(false)
                .error(error)
                .build();
    }

    public static <R> ApiResultDto<R> fail(R data, ErrorResultResponseDto error) {
        return ApiResultDto.<R>builder()
                .isSuccess(false)
                .data(data)
                .error(error)
                .build();
    }
}
