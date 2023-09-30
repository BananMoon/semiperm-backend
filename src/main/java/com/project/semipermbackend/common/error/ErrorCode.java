package com.project.semipermbackend.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static javax.servlet.http.HttpServletResponse.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
//   common
    INVALID_REQUEST_DATA(SC_BAD_REQUEST, "요청 데이터가 유효하지 않습니다."),
//   login
    LOGIN_DISABLE_STATUS (SC_CONFLICT, "소셜 로그인 통해 사용자 조회 중 문제 발생하였습니다."),
    INVALID_RESPONSE_DATA(SC_NOT_FOUND, "소셜 로그인으로부터 전달된 응답 데이터가 유효하지 않습니다."),

    NOT_PROPER_SOCIALLOGIN_TYPE(SC_BAD_REQUEST, "적절하지 않은 소셜로그인 타입입니다."),
    NEED_SONAIL_AUTH_INFO(SC_NOT_ACCEPTABLE, "소셜로그인 인증되지 않은 사용자입니다. 인증이 필요합니다.(Account 계정 정보 X)"),

//   member, account
    NOT_FOUND_MEMBER(SC_NOT_FOUND, "존재하지 않는 회원입니다. 회원가입이 필요합니다."),
    NOT_FOUND_ACCOUNT(SC_NOT_FOUND, "해당 계정(account)이 존재하지 않습니다."),
//    token
    TOKEN_EXPIRED_ERROR(SC_BAD_REQUEST, "JWT 토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN_ERROR(SC_NOT_ACCEPTABLE, "지원하지 않는 JWT 토큰입니다."),
    JWT_ERROR(SC_NOT_ACCEPTABLE, "JWT 토큰 관련 문제 발생하였습니다."),
    ERROR_DURING_FILTER(SC_CONFLICT, "필터 수행 중 문제 발생하였습니다.")

    // post
    , NOT_FOUND_POST(SC_NOT_FOUND, "해당 게시글이 존재하지 않습니다.");


    private final int status;
    private final String message;

}
