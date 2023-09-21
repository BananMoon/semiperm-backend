package com.project.semipermbackend.auth.service;

import com.project.semipermbackend.auth.dto.AuthResponseDto;
import com.project.semipermbackend.auth.entity.CustomOAuth2UserDetails;
import com.project.semipermbackend.domain.account.Account;
import com.project.semipermbackend.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AccountRepository accountRepository;
    @Transactional
    public AuthResponseDto.OAuth2Success createAccount (Account account) {
        Account savedAccount = accountRepository.save(account);

        return new AuthResponseDto.OAuth2Success(savedAccount.getAccountId());
    }

    public Optional<Account> getAccount(CustomOAuth2UserDetails principal) {
        return accountRepository.findBySocialTypeAndEmail(principal.getSocialType(), principal.getEmail());
    }

}
