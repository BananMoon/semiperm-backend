package com.project.semipermbackend.auth.service;

import com.project.semipermbackend.auth.entity.CustomOAuth2UserDetails;
import com.project.semipermbackend.auth.entity.GoogleOAuth2UserDetails;
import com.project.semipermbackend.auth.entity.SocialType;
import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.domain.account.Account;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class GoogleLoadStrategy extends SocialLoadStrategy {
    @Override
    public HttpEntity<MultiValueMap<String, String>> prepareRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", JwtTokenProvider.TOKEN_PREFIX + accessToken);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        return new HttpEntity<>(params, headers);
    }

    @Override
    protected GoogleOAuth2UserDetails sendRequestToSocialApi(HttpEntity request) {
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(SocialType.GOOGLE.getUserInfoRequestUrl(),
                SocialType.GOOGLE.getMethod(),
                request,
                responseType);
        return makeOAuth2User(responseEntity.getBody());
    }

    @Override
    protected GoogleOAuth2UserDetails makeOAuth2User(Map<String, Object> attributes) {
        return GoogleOAuth2UserDetails.builder()
                .socialType(SocialType.GOOGLE)
                .profileImgUrl(attributes.get("picture").toString())
                .socialId(attributes.get("id").toString())
                .email(attributes.get("email").toString())
                .build();
    }

    @Override
    public Account makeAccount(CustomOAuth2UserDetails oAuth2User) {
        return Account.builder()
                .socialId(oAuth2User.getSocialId())
                .socialType(SocialType.GOOGLE)
                .email(oAuth2User.getEmail())
                .profileImageUrl(oAuth2User.getProfileImgUrl())
                .build();
    }
}
