package com.project.semipermbackend.auth.security;

import com.project.semipermbackend.auth.entity.CustomOAuth2UserDetails;
import com.project.semipermbackend.auth.service.CustomOAuth2UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 2. OAuth2LoginAuthenticationFilter에서 OAuth2LoginAuthenticationProvider 호출
 * - 화면에서 입력/소셜로부터 전달받은 로그인 정보와 DB에서 가져온 사용자의 정보를 비교해주는 인터페이스
 */
// TODO 아예 oauth-client 라이브러리 통해 처리하는 거면 필요없는 클래스임.!!!!!
@Component
@AllArgsConstructor
public class OAuth2AuthenticationProvider implements AuthenticationProvider {
    private final CustomOAuth2UserDetailsService oAuth2UserService;
    /**
     * ProviderManager.authenticate() 내 로직에서 호출
     * 인증 객체 반환 메서드
     * - oAuth2UserService를 사용하여 회원 정보를 조회하여 Authentication 객체를 반환한다.
      */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 사용자 조회 API 호출
        CustomAuthenticationToken customAuthentication = (CustomAuthenticationToken) authentication;
        CustomOAuth2UserDetails customOAuth2UserDetails = oAuth2UserService.getCustomOAuth2User(customAuthentication);    // customAuthentication1

        // TODO 사용자 조회 결과가 없을 때 ErrorHandler로 가나??

        return CustomAuthenticationToken.builder()
                .principal(customOAuth2UserDetails)
                .authorities(customOAuth2UserDetails.getAuthorities())   // null..
                .build();
        //AccessTokenSocialTypeToken객체를 반환한다. principal은 OAuth2UserDetails객체이다. (formLogin에서는 UserDetails를 가져와서 결국 ContextHolder에 저장하기 때문에)
        //이렇게 구현하면 UserDetails 타입으로 회원의 정보를 어디서든 조회할 수 있다.
    }


    // AuthenticationManager가 Provider 순회하며 특정 Authentication(OAuth2AccessToken 타입의 authentication 객체)을 지원하는 Provider라면 처리한다
    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
