package com.project.semipermbackend.domain.post;

import com.project.semipermbackend.common.utils.PostCategoryConverter;
import com.project.semipermbackend.common.utils.SurgeryCategoryConverter;
import com.project.semipermbackend.domain.code.PostCategory;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import com.project.semipermbackend.domain.comment.Comment;
import com.project.semipermbackend.domain.common.BaseTimeEntity;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.post.dto.PostUpdate;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE post SET use_yn = false WHERE post_id = ?")
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE) // cascade: 특정 Entity의 영속성 상태가 변경 되었을 때, 이와 연관된(연관관계를 맺고 있는) Entity로의 전파 선택 옵션
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addLike() {
        likeCount += 1;
    }

    public void addViewCount() {
        viewCount += 1;
    }

    public void update(PostUpdate.RequestDto requestDto) {
        this.content = requestDto.getContent();
        this.title = requestDto.getTitle();
        this.surgeryCategory = requestDto.getSurgeryCategory();
        this.postCategory = requestDto.getPostCategory();
    }
}
