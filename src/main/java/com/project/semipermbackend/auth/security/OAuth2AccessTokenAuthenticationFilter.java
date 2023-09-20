package com.project.semipermbackend.auth.security;

import com.project.semipermbackend.auth.entity.SocialType;
import com.project.semipermbackend.auth.exception.NotProperSocialLoginTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. 로그인 요청 시 동작하는 구현체. 인증 처리 후 Jwt 토큰 발급.
 * UsernamePasswordAuthenticationFilter.java 앞에서 수행된다.
 * attemptAuthentication() : AuthenticationManager의 authenticate() 호출하여 인증 진행  -> 여기서는 Provider의 authenticate() 호출.
 * - 이때 인자로, Authentication 구현체인 SocialAccessToken 전달
 */
@Component
@Slf4j
public class OAuth2AccessTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String DEFAULT_OAUTH2_LOGIN_REQUEST_URL_PREFIX = "/oauth2/login/";
    private static final String REQUEST_HTTP_METHOD = "POST";
    private static final String ACCESS_TOKEN_HEADER_NAME = "Authorization";
    private static final AntPathRequestMatcher DEFAULT_OAUTH2_LOGIN_REQUEST_MATCHER = new AntPathRequestMatcher
            (DEFAULT_OAUTH2_LOGIN_REQUEST_URL_PREFIX + "*", REQUEST_HTTP_METHOD);

    public OAuth2AccessTokenAuthenticationFilter(OAuth2AuthenticationProvider OAuth2AuthenticationProvider,
                                                 AuthenticationSuccessHandler authenticationSuccessHandler,
                                                 AuthenticationFailureHandler authenticationFailureHandler) {
        super(DEFAULT_OAUTH2_LOGIN_REQUEST_MATCHER);
        this.setAuthenticationManager(new ProviderManager(OAuth2AuthenticationProvider));    // 커스텀 Provider를 포함시킨 ProviderManager 세팅
        this.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        this.setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    /**
     * 전달받은 요청 객체에서 소셜 로그인 및 AccessToken 추출 후 AuthenticationManager에게 인증 요청
     * - 인증된 authentication 객체 반환
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // request에서 SocialType 추출
        SocialType socialType = extractSocialTypeFrom(request);

        // 소셜 로그인 위한 access token 추출
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER_NAME);
        log.info("소셜 로그인 타입 : {}", socialType.getSocialName());

        // AuthenticationManager에게 인증 요청
        return this.getAuthenticationManager().authenticate(new SocialTypeAccessToken(accessToken, socialType));    // CustomOAuth2 객체 있어??
    }

    private SocialType extractSocialTypeFrom(HttpServletRequest request) {
        String requestSocialName = request.getRequestURI().substring(DEFAULT_OAUTH2_LOGIN_REQUEST_URL_PREFIX.length());
        for (SocialType st : SocialType.values()) {
            if (st.getSocialName().equals(requestSocialName)) {
                return st;
            }
        }
        throw new NotProperSocialLoginTypeException();
    }
}
