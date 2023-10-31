package com.project.semipermbackend.domain.store;

import com.project.semipermbackend.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberZzimStoreRepository extends JpaRepository<MemberZzimStore, Long>, MemberZzimStoreRepositoryCustom {

    Optional<MemberZzimStore> findByMemberAndStore(Member member, Store store);

    Page<MemberZzimStore> findAll(Pageable pageable);
}
