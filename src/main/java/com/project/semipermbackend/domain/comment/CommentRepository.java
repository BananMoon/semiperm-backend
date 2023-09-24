package com.project.semipermbackend.domain.comment;

import com.project.semipermbackend.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findPageByPostOrderByCreatedDateAsc(Pageable pageable, Post post);

    CommentGroupNoMapping findTopOrderByGroupNoDesc();
}
