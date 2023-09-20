package com.project.semipermbackend.auth.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.oauth2.core.user.OAuth2User;

@SuperBuilder
@Getter
public abstract class CustomOAuth2User implements OAuth2User {
    SocialType socialType;
    String socialId;
    String email;
    String userName;

    String profileImgUrl;

    protected CustomOAuth2User (SocialType socialType, String socialId, String email, String profileImgUrl) {
        this.socialType = socialType;
        this.socialId = socialId;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
    }


}
