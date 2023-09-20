package com.project.semipermbackend.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.semipermbackend.common.dto.ApiResultDto;
import com.project.semipermbackend.common.error.ErrorResultResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.project.semipermbackend.common.error.ErrorCode.LOGIN_DISABLE_STATUS;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2FailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("로그인에 실패하였습니다.");

        ErrorResultResponseDto errorDto = new ErrorResultResponseDto(LOGIN_DISABLE_STATUS.name(), LOGIN_DISABLE_STATUS.getMessage());
        ApiResultDto apiResultDto = ApiResultDto.fail(errorDto);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getWriter(), apiResultDto);
    }
}
