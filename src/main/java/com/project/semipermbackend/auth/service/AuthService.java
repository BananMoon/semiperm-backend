package com.project.semipermbackend.auth.service;

import com.project.semipermbackend.auth.dto.AuthResponseDto;
import com.project.semipermbackend.domain.account.Account;
import com.project.semipermbackend.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AccountRepository accountRepository;
    @Transactional
    public AuthResponseDto.OAuth2Success createAccount (Account account) {
        Account savedAccount = accountRepository.save(account);

        return new AuthResponseDto.OAuth2Success(savedAccount.getAccountId());
    }

}
