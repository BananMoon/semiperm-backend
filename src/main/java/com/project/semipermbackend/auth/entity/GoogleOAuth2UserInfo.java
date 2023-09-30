package com.project.semipermbackend.auth.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@SuperBuilder
public class GoogleOAuth2UserInfo extends CustomOAuth2UserInfo {

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return null;
    }
}
