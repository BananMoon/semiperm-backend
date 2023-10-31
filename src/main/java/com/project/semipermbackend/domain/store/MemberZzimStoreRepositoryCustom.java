package com.project.semipermbackend.domain.store;

import com.project.semipermbackend.domain.code.PostSorting;
import com.project.semipermbackend.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberZzimStoreRepositoryCustom {
    Page<MemberZzimStore> findAllByMemberOrderBy(Pageable pageable, Member member, PostSorting sorting);

}
