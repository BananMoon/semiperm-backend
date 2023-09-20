package com.project.semipermbackend.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.semipermbackend.auth.dto.AuthResponseDto;
import com.project.semipermbackend.auth.entity.CustomOAuth2User;
import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.auth.service.AuthService;
import com.project.semipermbackend.auth.service.CustomOAuth2UserService;
import com.project.semipermbackend.common.code.FlagYn;
import com.project.semipermbackend.common.dto.ApiResultDto;
import com.project.semipermbackend.domain.account.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final CustomOAuth2UserService customOauth2UserService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();

        // 0. Account 조회
        Optional<Account> optionalAccount = customOauth2UserService.getAccount(principal);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        if (optionalAccount.isEmpty()) {
            Account newAccount = Account.builder()
                    .socialId(principal.getSocialId())
                    .email(principal.getEmail())
                    .profileImageUrl(principal.getProfileImgUrl())
                    .socialType(principal.getSocialType())
                    .build();
            AuthResponseDto.OAuth2Success resultApiDto = authService.createAccount(newAccount);
            log.info("계정 생성에 성공하였습니다.(accountSeq : {})", resultApiDto.getAccountId());

            /*성공 응답 케이스*/
            ApiResultDto apiResultDto = ApiResultDto.success(resultApiDto);

            /** 실패 응답 케이스
             MemberCreation.ResponseDto responseDto = new MemberCreation.ResponseDto(savedAccount.getAccountId());
             ErrorResultResponseDto errorDto = new ErrorResultResponseDto(NOT_FOUND_MEMBER.name(), NOT_FOUND_MEMBER.getMessage());

             ApiResultDto apiResultDto = ApiResultDto.fail(resultApiDto, errorDto); */

            objectMapper.writeValue(response.getWriter(), apiResultDto);
            return;
        }
        Account account = optionalAccount.get();
        //  1. MemberYn : 회원이면 로그인
        if (isPresentAndMember(optionalAccount)) {

            // SocialId, email 두 값을 jwt에 넣어놀까?
            String email = account.getEmail();

            String accessToken = jwtTokenProvider.createAccessToken(email);
            String refreshToken = jwtTokenProvider.createRefreshToken(email);

            // 로그인
            account.login(refreshToken);
            AuthResponseDto.Login responseDto = new AuthResponseDto.Login(accessToken, refreshToken);
            ApiResultDto<AuthResponseDto.Login> apiResult = ApiResultDto.success(responseDto);

            objectMapper.writeValue(response.getWriter(), apiResult);
            return;
        }
        //  2. MemberYn : 회원아니면 회원가입 창
        if (isPresentButNotMember(optionalAccount)) {
            AuthResponseDto.OAuth2Success resultApiDto = new AuthResponseDto.OAuth2Success(account.getAccountId());
            ApiResultDto apiResultDto = ApiResultDto.success(resultApiDto);
            objectMapper.writeValue(response.getWriter(), apiResultDto);
        }

    }

    private boolean isPresentAndMember(Optional<Account> optionalAccount) {
        return optionalAccount.isPresent() && FlagYn.YES.equals(optionalAccount.get().getMemberYn());
    }

    private boolean isPresentButNotMember(Optional<Account> optionalAccount) {
        return FlagYn.NO.equals(optionalAccount.get().getMemberYn());
    }

}
