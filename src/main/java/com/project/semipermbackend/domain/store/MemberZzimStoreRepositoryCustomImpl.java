package com.project.semipermbackend.domain.store;

import com.project.semipermbackend.domain.code.PostSorting;
import com.project.semipermbackend.domain.member.Member;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.project.semipermbackend.domain.store.QMemberZzimStore.memberZzimStore;

@RequiredArgsConstructor
public class MemberZzimStoreRepositoryCustomImpl implements MemberZzimStoreRepositoryCustom {
    private final JPAQueryFactory query;

    // TODO 테스트 필요
    @Override
    public Page<MemberZzimStore> findAllByMemberOrderBy(Pageable pageable, Member member, PostSorting sorting) {
        List<MemberZzimStore> memberZzimStores = query
                .select(memberZzimStore)
                .from(memberZzimStore)
                .where(memberZzimStore.member.memberId.eq(member.getMemberId()))
                .orderBy(
                        postSortingEq(sorting)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = query
                .select(memberZzimStore.count())
                .where(memberZzimStore.member.memberId.eq(member.getMemberId()))
                .from(memberZzimStore)
                .fetchOne();

        return new PageImpl<>(memberZzimStores, pageable, totalCount);
    }

    private OrderSpecifier[] postSortingEq(PostSorting postSorting) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        switch (postSorting) {
            case REVIEW_COUNT -> orderSpecifiers.add(new OrderSpecifier(Order.DESC, memberZzimStore.store.reviews.size()));
            case LATEST -> orderSpecifiers.add(new OrderSpecifier(Order.DESC, memberZzimStore.createdDate));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

}
