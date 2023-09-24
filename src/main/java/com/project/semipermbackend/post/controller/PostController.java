package com.project.semipermbackend.post.controller;

import com.project.semipermbackend.common.dto.ApiResultDto;
import com.project.semipermbackend.common.dto.Pagination;
import com.project.semipermbackend.domain.code.PostCategory;
import com.project.semipermbackend.domain.code.PostSorting;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import com.project.semipermbackend.post.dto.PostCreation;
import com.project.semipermbackend.post.dto.PostViewDto;
import com.project.semipermbackend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/community/post")
@RestController
public class PostController {
    private final PostService postService;

    // 게시글 등록
    @PostMapping
    public ResponseEntity<ApiResultDto<PostCreation.ResponseDto>> postCreation(@Valid @RequestBody PostCreation.RequestDto postCreation) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        PostCreation.ResponseDto responseDto = postService.create(Long.valueOf(principal.getUsername()), postCreation);

        return new ResponseEntity<>(ApiResultDto.success(responseDto), HttpStatus.CREATED);
    }



    // 게시글 상세 조회 (No Filter, No Sort)
    // default는 ALL, 그외는 SurgeryCategory(SMP)
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResultDto<PostViewDto.Response>> getPostDetail(@PathVariable Long postId) {

        PostViewDto.Response response = postService.getOne(postId);
        return new ResponseEntity<>(ApiResultDto.success(response), HttpStatus.CREATED);

    }


    /**
     * 게시글 전체 조회 (sortedPost)
     * sorting 필드에 따라 정렬하여 페이징 처리
     * @param page
     * @param perSize
     * @param filteredSurgeryCategory 시술 카테고리 필터링
     * @param postSorting 필드에 따라 정렬 (최신순, 인기순, 좋아요순) - 좋아요순 vs 인기순 차이?
     * @param filteredPostCategory 게시글 성격 필터링 (전체, 고민, 정보, 자유게시글)
     */
    @GetMapping("/post")
    public Pagination<ApiResultDto<PostViewDto.Response>> getAllPost (
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer perSize,
            @RequestParam(defaultValue = "ALL", required = false) SurgeryCategory filteredSurgeryCategory, // TODO enum.name()이 통하려나?
            @RequestParam(defaultValue = "NEW", required = false) PostSorting postSorting,
            @RequestParam(defaultValue = "ALL", required = false) PostCategory filteredPostCategory
    ) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        postService.getAll(page-1, perSize, principal.getUsername(), getAllPost);


        return null;

    }

    // 게시글 좋아요


}
