package com.project.semipermbackend.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.semipermbackend.auth.dto.AuthResponseDto;
import com.project.semipermbackend.auth.entity.CustomOAuth2UserInfo;
import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.auth.service.AuthService;
import com.project.semipermbackend.common.code.FlagYn;
import com.project.semipermbackend.common.dto.ApiResultDto;
import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.exception.EntityNotFoundException;
import com.project.semipermbackend.domain.account.Account;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.member.service.MemberService;
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

/**
 * 역할 : 기존 UserDetails의 service 메서드가 할 일을 대신한다.
 * 1. Account 조회
 * 1.1 존재하지 않으면 계정 생성 후 회원가입 유도 (Success 응답)
 * 1.2. 존재하면 회원인지 여부 확인
 * 2. MemberYn = Y?
 * 2.1. Y이면 토큰 생성 -> 로그인 (Success 응답)
 * 2.2. N이면 회원가입 유도 (Success 응답, 인증은 했으나 회원가입하지 않은 케이스)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final MemberService memberService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2UserInfo principal = (CustomOAuth2UserInfo) authentication.getPrincipal();

        // 0. Account 조회
        Optional<Account> optionalAccount = authService.getAccount(principal);

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
            ApiResultDto<AuthResponseDto.OAuth2Success> apiResultDto = ApiResultDto.success(resultApiDto);

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
            Member member = memberService.getMemberByAccount(account)
                    .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MEMBER, account.getAccountId()));

            // memberId, accountId를 세팅
            String accessToken = jwtTokenProvider.createAccessToken(member, account);
            String refreshToken = jwtTokenProvider.createRefreshToken(member, account);

            // 로그인
            account.setLoginStatus(refreshToken);
            AuthResponseDto.AuthTokens responseDto = new AuthResponseDto.AuthTokens(accessToken, refreshToken);
            ApiResultDto<AuthResponseDto.AuthTokens> apiResult = ApiResultDto.success(responseDto);

            objectMapper.writeValue(response.getWriter(), apiResult);
            return;
        }
        //  2. MemberYn : 회원아니면 회원가입 창
        if (isPresentButNotMember(optionalAccount)) {
            AuthResponseDto.OAuth2Success resultApiDto = new AuthResponseDto.OAuth2Success(account.getAccountId());
            ApiResultDto<AuthResponseDto.OAuth2Success> apiResultDto = ApiResultDto.success(resultApiDto);
            objectMapper.writeValue(response.getWriter(), apiResultDto);
        }

    }

    private boolean isPresentAndMember(Optional<Account> optionalAccount) {
        return optionalAccount.isPresent() && FlagYn.YES.equals(optionalAccount.get().getMemberYn());
    }

    private boolean isPresentButNotMember(Optional<Account> optionalAccount) {
        return optionalAccount.isPresent() && FlagYn.NO.equals(optionalAccount.get().getMemberYn());
    }

}
