package com.project.semipermbackend.auth.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@SuperBuilder
@Getter
public class KakaoOAuth2UserDetails extends CustomOAuth2UserDetails {
//    account_email profile_image profile_nickname
//    String profileImageUrl;
    String nickname;

    protected KakaoOAuth2UserDetails(String socialId, String email, SocialType socialType,
                                     String profileImageUrl, String nickname) {
        super(socialType, socialId, email, profileImageUrl);
//        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return super.userName;
    }
}
