package com.project.semipermbackend.domain.review;

import com.project.semipermbackend.common.utils.ReviewCategoryConverter;
import com.project.semipermbackend.domain.code.ReviewCategory;
import com.project.semipermbackend.domain.common.BaseTimeEntity;
import com.project.semipermbackend.domain.store.Store;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLDelete(sql = "UPDATE post SET use_yn = false WHERE post_id = ?")
@Where(clause = "use_yn = true")
@Table(name = "review")
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    private String content;

    private float rating;

    @Enumerated(EnumType.STRING)
    @Convert(converter = ReviewCategoryConverter.class)
    @Column(name = "review_category", nullable = false, length = 50)
    private ReviewCategory reviewCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
}
