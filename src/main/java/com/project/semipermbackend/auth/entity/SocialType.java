package com.project.semipermbackend.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
@AllArgsConstructor
public enum SocialType {

    GOOGLE("google", "https://www.googleapis.com/userinfo/v2/me", HttpMethod.GET)
    , KAKAO ("kakao", "https://kapi.kakao.com/v2/user/me", HttpMethod.GET)
    , NAVER("naver", "https://openapi.naver.com/v1/nid/me", HttpMethod.GET)
    , APPLE("apple", "", HttpMethod.GET)
    ;

    private final String socialName;
    private final String UserInfoRequestUrl;
    private final HttpMethod method;

}
