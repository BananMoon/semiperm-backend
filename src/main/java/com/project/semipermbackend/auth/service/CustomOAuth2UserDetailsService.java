package com.project.semipermbackend.auth.service;
import com.project.semipermbackend.auth.entity.CustomOAuth2UserInfo;
import com.project.semipermbackend.auth.entity.SocialType;
import com.project.semipermbackend.auth.security.CustomAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 사용자 정보는 SuccessHandler에서 진행
 * Authentication 인증 객체로
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserDetailsService {

    /**
     * 이용 소셜 로그인에 따라 사용자 정보 호출 api 수행.
     */
    public CustomOAuth2UserInfo getCustomOAuth2User(CustomAuthentication authentication) {
        SocialType socialType = authentication.getSocialType();
        String accessToken = authentication.getAccessToken();

        SocialLoadStrategy socialLoadStrategy = SocialLoadStrategy.getSocialLoadStrategy(socialType);

        // 사용자 정보 조회 api 호출.
        CustomOAuth2UserInfo oAuth2User = socialLoadStrategy.getOAuth2User(accessToken);

        return oAuth2User;
    }

}