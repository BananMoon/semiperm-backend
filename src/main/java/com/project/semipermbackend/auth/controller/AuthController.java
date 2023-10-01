package com.project.semipermbackend.auth.controller;

import com.project.semipermbackend.auth.dto.AuthResponseDto;
import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.auth.service.AuthService;
import com.project.semipermbackend.common.dto.ApiResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PutMapping("/token-reissue")
    public ResponseEntity<ApiResultDto<AuthResponseDto.AuthTokens>> reissue() {
        Long accountId = JwtTokenProvider.getAccountIdFromContext();

        AuthResponseDto.AuthTokens reissuedTokens  = authService.reissueTokens(accountId);
        return new ResponseEntity<>(ApiResultDto.success(reissuedTokens), HttpStatus.ACCEPTED);
    }

}
