package com.project.semipermbackend.auth.security.filter;

import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 요청 내 헤더에 있는 JWT의 유효성 체크하는 필터.
 * - jwt가 있고 유효할 경우 JWT 내 페이로드를 SecurityContextHolder에 저장한다.
 * - jwt가 없을 경우 다음 필터로 넘긴다.
 * - jwt가 유효하지 않으면 유효하지 않다는 에러를 반환한다. (jwtTokenProvider.validateToken 메서드 참고)
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class AuthenticationCheckFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    /**
     * 헤더의 JWT 추출하여 존재하면 객체 정보 셋팅
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = extractToken(request);
        // 토큰에서 인증 정보 추출
        if (StringUtils.hasText(jwt)) { // TODO 여기가 문제 같은데
            jwtTokenProvider.validateToken(jwt);
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
            return accessToken.substring(JwtTokenProvider.TOKEN_PREFIX.length());
        }
        // TODO Exception Handling : 적절한 jwt가 없음.
        return null;
    }
}
