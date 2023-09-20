package com.project.semipermbackend.domain.account;

import com.project.semipermbackend.auth.entity.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findBySocialTypeAndEmail(SocialType socialType, String email);

    Optional<Account> findByAccountId(Long accountId);
}
