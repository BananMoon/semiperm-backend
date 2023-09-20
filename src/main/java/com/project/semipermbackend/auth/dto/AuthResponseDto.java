package com.project.semipermbackend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AuthResponseDto {
    // 로그인 성공 시 Response
    @Getter
    @AllArgsConstructor
    public static class Login {
        String accessToken;
        String refreshToken;
    }

    // sns 인증 성공 시 Response
    @Getter
    @AllArgsConstructor
    public static class OAuth2Success {
        Long accountId;
    }
}
