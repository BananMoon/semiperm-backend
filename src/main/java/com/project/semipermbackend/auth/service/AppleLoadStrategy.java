package com.project.semipermbackend.auth.service;

import com.project.semipermbackend.auth.entity.CustomOAuth2User;
import com.project.semipermbackend.auth.entity.SocialType;
import com.project.semipermbackend.domain.account.Account;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import java.util.Map;
// TODO
public class AppleLoadStrategy extends SocialLoadStrategy {

    @Override
    public HttpEntity<MultiValueMap<String, String>> prepareRequest(String accessToken) {
        return null;
    }

    @Override
    protected CustomOAuth2User sendRequestToSocialApi(HttpEntity<MultiValueMap<String, String>> request) {
        return null;
    }

    @Override
    protected CustomOAuth2User makeOAuth2User(Map<String, Object> attributes) {
        return null;
    }

    @Override
    public Account makeAccount(CustomOAuth2User oAuth2User) {
        return Account.builder()
                .socialId(oAuth2User.getSocialId())
                .socialType(SocialType.APPLE)
                .email(oAuth2User.getEmail())
                .profileImageUrl(oAuth2User.getProfileImgUrl()) // TODO 전달해주는지 확인 필요
                .build();
    }

}
