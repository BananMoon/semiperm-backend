package com.project.semipermbackend.domain.post;

import com.project.semipermbackend.common.utils.PostCategoryConverter;
import com.project.semipermbackend.common.utils.SurgeryCategoryConverter;
import com.project.semipermbackend.domain.code.PostCategory;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import com.project.semipermbackend.domain.common.BaseTimeEntity;
import com.project.semipermbackend.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "use_yn = true")
@Entity
@Table(name = "post")
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)  // Member 없는 Post 허용 X
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @Enumerated(EnumType.STRING)
    @Convert(converter = PostCategoryConverter.class)
    @Column(name = "post_category", nullable = false, length = 30)
    private PostCategory postCategory;

    @Enumerated(EnumType.STRING)
    @Convert(converter = SurgeryCategoryConverter.class)
    @Column(name = "surgery_category", nullable = false, length = 30)
    private SurgeryCategory surgeryCategory;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;
}
