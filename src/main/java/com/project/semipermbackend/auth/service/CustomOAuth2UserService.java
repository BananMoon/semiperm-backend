package com.project.semipermbackend.auth.service;
import com.project.semipermbackend.auth.entity.CustomOAuth2User;
import com.project.semipermbackend.auth.entity.OAuth2UserInfoFactory;
import com.project.semipermbackend.auth.entity.SocialType;
import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.auth.security.SocialTypeAccessToken;
import com.project.semipermbackend.domain.account.Account;
import com.project.semipermbackend.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

/** 사용하지 않는 클래스!!!!!!!!!!! (SuccessHandler에서 대신해주고 있음)
 *
 * 2. OAuth2LoginAuthenticationFilter에서 OAuth2UserService(구현: DefaultOAuth2UserService)의 loadUser() 호출 -> Custom!
 * 역할
 * - 회원가입 한 적이 있는지 확인 후 1) 회원가입 시키거나, 2) 정보 업데이트 & 로그인
 *
 * 참고
 * - 서드파티 접근을 위한 accessToken은 OAuth2UserRequest에 포함되어 있다.
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService { // implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("==========================CustomOAuth2UserService.loadUser() 실행==========================");

        Assert.notNull(userRequest, "userRequest cannot be null");

        // 1. restTemplate과 AccessToken을 가지고 회원 정보 요청
        OAuth2User oAuth2User = super.loadUser(userRequest);
        SocialType socialType = SocialType.fromSocialName(userRequest.getClientRegistration().getRegistrationId());
        if (Objects.isNull(socialType)) {
            // TODO Error Handling
            System.out.println("지원하지 않은 로그인 서비스 입니다.");
        }
        CustomOAuth2User customOAuth2User = OAuth2UserInfoFactory.getCustomOAuth2User(socialType, oAuth2User.getAttributes());


        // 회원 조회
        // 회원이 없으면 Member, Account 기본 데이터 저장하고, 회원가입하도록 ErrorCode 리턴 -> 지금은 FailureHandler. 없애도 될수도..
        findOrMakeAccountAndMember(customOAuth2User);

        // 회원 있으면 return Authentication -> SuccessHandler에서 JWT 생성 후 리턴 예정.
        return customOAuth2User;
    }

    private void findOrMakeAccountAndMember(CustomOAuth2User oAuth2User) {
        Optional<Account> optionalAccount = accountRepository.findBySocialTypeAndEmail(oAuth2User.getSocialType(), oAuth2User.getEmail());
        if (optionalAccount.isEmpty()) {
            accountRepository.save(Account.builder()

                    .build());
            // TODO ERROR Handling
            throw new OAuth2AuthenticationException("");
        }
    }

    /**
     * 이용 소셜 로그인에 따라 사용자 정보 호출 api 수행.
     */
    public CustomOAuth2User getCustomOAuth2User (SocialTypeAccessToken authentication) {
        SocialType socialType = authentication.getSocialType();
        String accessToken = authentication.getAccessToken();

        SocialLoadStrategy socialLoadStrategy = SocialLoadStrategy.getSocialLoadStrategy(socialType);

        // 사용자 정보 조회 api 호출.
        CustomOAuth2User oAuth2User = socialLoadStrategy.getOAuth2User(accessToken);

        return oAuth2User;
    }

    public Optional<Account> getAccount(CustomOAuth2User principal) {
        return accountRepository.findBySocialTypeAndEmail(principal.getSocialType(), principal.getEmail());
    }

}

/*
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final RestTemplate restTemplate = new RestTemplate();   // AccessToken 이용해서 사용자 정보 요청

    /**
     * 사용자 정보 호출 api 수행.
     * @param userRequest the user request
     * @return
     * @throws OAuth2AuthenticationException
     */
/*@Override
public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    System.out.println(userRequest.getAccessToken());
    System.out.println(oAuth2User.getName());
//        return getCustomOAuth2User(userRequest.getAccessToken().getTokenValue());
    return null;
}

}
*/