package com.project.semipermbackend.domain.member;

import com.project.semipermbackend.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByMemberId(Long memberId);

    Member findByAccount(Account account);
}
