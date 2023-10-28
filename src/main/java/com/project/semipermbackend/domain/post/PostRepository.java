package com.project.semipermbackend.domain.post;

import com.project.semipermbackend.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Optional<Post> findByPostId(Long postId);

    Page<Post> findAllByMember(Pageable pageable, Member member);

    void deleteByPostId(Long postId);
}
