package com.project.semipermbackend.domain.post;

import com.project.semipermbackend.domain.code.PostCategory;
import com.project.semipermbackend.domain.code.PostSorting;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.project.semipermbackend.domain.post.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public Page<Post> findAllByFilteringOrderByDefaultCreatedDateDesc(Pageable pageable, SurgeryCategory surgeryCategory, PostCategory postCategory, PostSorting postSorting) {

        List<Post> posts = query
                .select(post)
                .from(post)
                .where(
                        postCategoryEq(postCategory)
                                .and(surgeryCategoryEq(surgeryCategory))
                )
                .orderBy(
                        postSortingEq(postSorting)
                )
                .orderBy(post.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = query
                .select(post.count())
                .from(post)
                .where(
                        postCategoryEq(postCategory)
                                .and(surgeryCategoryEq(surgeryCategory))
                )
                .fetchOne();

        return new PageImpl<>(posts, pageable, totalCount);
    }

    private OrderSpecifier[] postSortingEq(PostSorting postSorting) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        switch (postSorting) {
            case POPULARITY -> {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, post.comments.size()));
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, post.likeCount));
            }
            case LIKE -> {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, post.likeCount));
            }
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    private BooleanBuilder postCategoryEq(PostCategory filteredPostCategory) {
        return !(Objects.isNull(filteredPostCategory) || filteredPostCategory.equals(PostCategory.TOTAL)) ? new BooleanBuilder(post.postCategory.eq(filteredPostCategory)) : new BooleanBuilder();
    }

    private BooleanBuilder surgeryCategoryEq(SurgeryCategory filteredSurgeryCategory) {
        return !(Objects.isNull(filteredSurgeryCategory) || filteredSurgeryCategory.equals(SurgeryCategory.ALL)) ? new BooleanBuilder(post.surgeryCategory.eq(filteredSurgeryCategory)) : new BooleanBuilder();
    }
    @Override
    public Page<Post> findAllByTitleOrContentIsContainsIgnoreCaseOrderBySorting(Pageable pageable, String keyword, PostSorting sorting) {
        List<Post> posts = query
                .select(post)
                .from(post)
                .where(
                        postTitleContains(keyword)
                                .or(postContentContains(keyword))
                )
                .orderBy(postSortingEq(sorting))
                .orderBy(post.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = query
                .select(post.count())
                .from(post)
                .where(
                        postTitleContains(keyword)
                                .or(postContentContains(keyword))
                )
                .fetchOne();
        return new PageImpl<>(posts, pageable, totalCount);

    }

    private BooleanBuilder postContentContains(String keyword) {
        return !Objects.isNull(keyword) ? new BooleanBuilder(post.content.containsIgnoreCase(keyword)) : new BooleanBuilder();
    }

    // likeIgnoreCase : %를 수동으로 입력해줘야함.
    // containsIgnoreCase : 자동. like '%%'
    private BooleanBuilder postTitleContains(String keyword) {
        return !Objects.isNull(keyword) ? new BooleanBuilder(post.title.containsIgnoreCase(keyword)) : new BooleanBuilder();
    }

}
