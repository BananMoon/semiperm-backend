package com.project.semipermbackend.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Open API에 사용자 정보를 요청하여 응답 온 데이터이다.
 * JWT 페이로드에 사용된다.
 * - 현재는 email만 포함.
 */
@SuperBuilder
@Getter
public abstract class CustomOAuth2UserInfo implements OAuth2User {
    SocialType socialType;
    String socialId;
    String email;
    String userName;

    String profileImgUrl;


    @Getter
    private Long memberId;

    protected CustomOAuth2UserInfo(SocialType socialType, String socialId, String email, String profileImgUrl) {
        this.socialType = socialType;
        this.socialId = socialId;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
    }


}
