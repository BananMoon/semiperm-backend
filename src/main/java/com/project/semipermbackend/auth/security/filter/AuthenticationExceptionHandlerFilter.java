package com.project.semipermbackend.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.semipermbackend.auth.exception.LoginDisableException;
import com.project.semipermbackend.auth.exception.NotProperSocialLoginTypeException;
import com.project.semipermbackend.auth.exception.TokenInvalidException;
import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.ErrorResultResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
public class AuthenticationExceptionHandlerFilter extends OncePerRequestFilter {
    private ObjectMapper objectMapper;

    @PostConstruct
    protected void init() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (NotProperSocialLoginTypeException ex) {
            setErrorResponse(response, ex.getErrorCode(), ex.getMessage());

        } catch (TokenInvalidException ex) {
            setErrorResponse(response, ex.getErrorCode(), ex.getMessage());

        } catch (LoginDisableException ex) {
            setErrorResponse(response, ex.getErrorCode(), ex.getMessage());

        } catch (Exception ex) {    // 그외
            setErrorResponse(response, ErrorCode.ERROR_DURING_FILTER, ex.getMessage());
        }
        filterChain.doFilter(request, response);
    }


    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode, String msg) {
        log.error(errorCode.name() + " : " + msg);
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ErrorResultResponseDto errorResponse = ErrorResultResponseDto.of(errorCode, msg);

        try {
            String json = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(json);
        } catch (IOException ex) {
            log.error("ErrorResponse 객체를 json으로 전환하는 데에 실패하였습니다. (ErrorResponse : %s)", errorResponse.getCode());
        }
    }
}