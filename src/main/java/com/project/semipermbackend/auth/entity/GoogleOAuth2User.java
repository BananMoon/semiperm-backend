package com.project.semipermbackend.auth.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

/**
 {
 "id": "113331245371216057262",
 "email": "younji1115@gmail.com",
 "verified_email": true,
 "name": "문윤지",
 "given_name": "윤지",
 "family_name": "문",
 "picture": "https://lh3.googleusercontent.com/a/AAcHTtcXokGzsG_2yHZQx4rfm7-Cz4nW92REuyg-J2wlBBGrrmg=s96-c",
 "locale": "ko"
 }
 */
@Getter
@SuperBuilder
public class GoogleOAuth2User extends CustomOAuth2User {
//    String profileImgUrl;

    protected GoogleOAuth2User(SocialType socialType, String socialId, String email, String profileImageUrl) {
        super(socialType, socialId, email, profileImageUrl);
//        this.profileImageUrl = profileImageUrl;
    }
    // nickname 없네..

    // TODO 얘네 어떻게 없애지?
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
        return null;
    }
}
