package com.project.semipermbackend.auth.entity;


import java.util.Map;
/** 사용하지 않는 클래스!!!!!!!!!!!
*/

public class OAuth2UserInfoFactory {
    public static CustomOAuth2UserInfo getCustomOAuth2User(SocialType socialType, Map<String, Object> attributes) {
        switch (socialType) {
            case APPLE: return AppleOAuth2UserInfo.builder()
                    .socialType(socialType)
                    .build();
//            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            case NAVER: return NaverOAuth2UserInfo.builder()
                    .socialType(socialType)
                    .build();
//            case KAKAO: return KakaoOAuth2User.builder()
//            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("유효하지 않은 소셜 로그인 타입입니다.");
        }
    }
}
