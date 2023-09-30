package com.project.semipermbackend.member.service;

import com.project.semipermbackend.common.error.ErrorCode;
import com.project.semipermbackend.common.error.exception.EntityNotFoundException;
import com.project.semipermbackend.member.exception.UnauthenticatedUserException;
import com.project.semipermbackend.common.utils.StringUtils;
import com.project.semipermbackend.domain.account.Account;
import com.project.semipermbackend.domain.account.AccountRepository;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.domain.member.MemberRepository;
import com.project.semipermbackend.member.dto.MemberCreation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    @Transactional
    public void join(MemberCreation.RequestDto memberCreation) {
        // 1. 계정 테이블에서 조회
        Account account = accountRepository.findByAccountId(memberCreation.getAccountId())
                .orElseThrow(UnauthenticatedUserException::new);

        // TODO 모두 Y인지 유효성 체크
        account.saveAgreeYnInfos(memberCreation);

        // 2. 멤버 테이블 insert
        String randomNickname = StringUtils.makeRandomNickname(account.getSocialType());
        // TODO 생성된 닉네임이 중복인지 체크 필요

        Member member = Member.builder()
                .gender(memberCreation.getGender())
                .birth(memberCreation.getBirth())
                .account(account)
                .nickname(randomNickname)
                .interestingFields(memberCreation.getInterestingFields())
                .needInformations(memberCreation.getNeedInformations())
                .build();

        // 3. 회원가입 처리 (insert)
        memberRepository.save(member);
        account.saveAgreeYnInfos(memberCreation);
        account.joinSuccess();
    }

    public Member getMemberByMemberId(Long memberId) {
        return memberRepository.findByMemberId(memberId);
    }

    public Optional<Member> getMemberByAccount(Account account) {
        return memberRepository.findByAccount(account);
    }

    @Transactional
    public void logout(Long accountId) {
        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ACCOUNT, accountId));
        account.logout();
    }
}