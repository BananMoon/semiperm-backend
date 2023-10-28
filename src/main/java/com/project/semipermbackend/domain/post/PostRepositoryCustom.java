package com.project.semipermbackend.domain.post;

import com.project.semipermbackend.domain.code.PostCategory;
import com.project.semipermbackend.domain.code.PostSorting;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> findAllByFilteringOrderByDefaultCreatedDateDesc(Pageable pageable, SurgeryCategory surgeryCategory, PostCategory postCategory, PostSorting postSorting);

    Page<Post> findAllByTitleOrContentIsContainsIgnoreCaseOrderBySorting(Pageable pageable, String keyword, PostSorting sorting);
}
