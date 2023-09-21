package com.project.semipermbackend.auth.service;

import com.project.semipermbackend.auth.entity.CustomOAuth2UserDetails;
import com.project.semipermbackend.auth.entity.SocialType;
import com.project.semipermbackend.auth.exception.LoginDisableException;
import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.domain.account.Account;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * 요청하는 대상 API마다 응답값이 다르므로 key, value(Object)로 응답 객체 생성한다. (RESPONES_TYPE)
 *
 */
public abstract class SocialLoadStrategy {
    ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};
    protected final RestTemplate restTemplate = new RestTemplate();

    public static SocialLoadStrategy getSocialLoadStrategy(SocialType socialType) {
        SocialLoadStrategy socialLoadStrategy = null;
        switch(socialType) {
            case APPLE:
                socialLoadStrategy = new AppleLoadStrategy();
                break;
            case NAVER: socialLoadStrategy = new NaverLoadStrategy();
                break;
            case KAKAO: socialLoadStrategy = new KakaoLoadStrategy();
                break;
            case GOOGLE: socialLoadStrategy = new GoogleLoadStrategy();
        }
        return socialLoadStrategy;
    }

    /**
     * Access Token 이용해 조회하면 응답받는 값 중 PK 존재 (소셜 타입마다 Response 구조 상이함)
     * @param accessToken
     */
    public CustomOAuth2UserDetails getOAuth2User (String accessToken) {
        HttpEntity<MultiValueMap<String, String>> request = prepareRequest(accessToken);
        CustomOAuth2UserDetails socialUserInfoResponse = null;
        try {
            socialUserInfoResponse = sendRequestToSocialApi(request);
        } catch (Exception e) {
            throw new LoginDisableException();
        }

        // API로부터 전달받은 응답값 없는 경우
        if (Objects.isNull(socialUserInfoResponse)) {
            throw new LoginDisableException(ErrorCode.INVALID_RESPONSE_DATA);
        }
        return socialUserInfoResponse;
    }

    public abstract HttpEntity<MultiValueMap<String, String>> prepareRequest(String accessToken);

    protected abstract CustomOAuth2UserDetails sendRequestToSocialApi(HttpEntity<MultiValueMap<String, String>> request);
    protected abstract CustomOAuth2UserDetails makeOAuth2User(Map<String, Object> attributes);
    public abstract Account makeAccount(CustomOAuth2UserDetails oAuth2User);
}
