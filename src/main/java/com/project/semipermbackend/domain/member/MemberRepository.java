package com.project.semipermbackend.domain.member;

import com.project.semipermbackend.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(Long memberId);

    Optional<Member> findByAccount(Account account);

    Boolean existsByNickname(String nickname);
}
