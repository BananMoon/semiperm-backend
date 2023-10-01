package com.project.semipermbackend.auth.service;

import com.project.semipermbackend.auth.dto.AuthResponseDto;
import com.project.semipermbackend.auth.entity.CustomOAuth2UserInfo;
import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.exception.EntityNotFoundException;
import com.project.semipermbackend.domain.account.Account;
import com.project.semipermbackend.domain.account.AccountRepository;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public AuthResponseDto.OAuth2Success createAccount (Account account) {
        Account savedAccount = accountRepository.save(account);

        return new AuthResponseDto.OAuth2Success(savedAccount.getAccountId());
    }

    public Optional<Account> getAccount(CustomOAuth2UserInfo principal) {
        return accountRepository.findBySocialTypeAndEmail(principal.getSocialType(), principal.getEmail());
    }

    @Transactional
    public AuthResponseDto.AuthTokens reissueTokens(Long accountId) {
        // Account, Member 조회
        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ACCOUNT, accountId));
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        return jwtTokenProvider.reissueTokens(member, account);
    }
}
