package com.project.semipermbackend.domain.comment;

import com.project.semipermbackend.comment.dto.CommentUpdateDto;
import com.project.semipermbackend.domain.common.BaseTimeEntity;
import com.project.semipermbackend.domain.member.Member;
import com.project.semipermbackend.domain.post.Post;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Builder
@SQLDelete(sql = "UPDATE comment SET use_yn = false WHERE comment_id = ?")
@Where(clause = "use_yn = true")
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "comment")
@DynamicUpdate
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String content;

    @Setter
    @Builder.Default
    @Column(name = "parent_id", nullable = false)
    private Long parentId = 0L;

    // 코멘트 정렬 조회를 위해 필요
    @Setter
    @Column(name = "group_no")
    private Long groupNo;

    @Setter
    @Column(name = "like_count")
    private int likeCount = 0;

    public void addLike() {
        this.likeCount += 1;
    }

    public void update(CommentUpdateDto.Request request) {
        this.content = request.getContent();
    }
}
