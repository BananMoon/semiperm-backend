package com.project.semipermbackend.auth.security;

import com.project.semipermbackend.auth.entity.SocialType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * Description : Authentication 구현체. SocialType과 AccessToken을 가지고, 인증 처리.
 * Author      : 문 윤지
 * History     : [2022-07-24] create Class
 */
public class CustomAuthentication extends AbstractAuthenticationToken {
    private Object principal;   // CustomOAuth2UserInfo
    @Getter
    private String accessToken;
    @Getter
    private SocialType socialType;

    private Long memeberId;

    public CustomAuthentication(String accessToken, SocialType socialType) {
        super(null);
        this.accessToken = accessToken;
        this.socialType = socialType;
        setAuthenticated(false);
    }

    @Builder
    public CustomAuthentication(Object principal, Collection<? extends GrantedAuthority> authorities) {
        // 권한 사용하지 않으므로..
        super(List.of());
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
