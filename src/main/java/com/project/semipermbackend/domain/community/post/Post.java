package com.project.semipermbackend.domain.community.post;

import com.project.semipermbackend.domain.code.Category;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Post {
    @Id
    @GeneratedValue
    private Long postId;

    private PostCategory postCategory;

    private Category category;

    private String title;

    private String content;
}
