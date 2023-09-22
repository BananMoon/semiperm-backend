package com.project.semipermbackend.post.controller;

import com.project.semipermbackend.common.dto.ApiResultDto;
import com.project.semipermbackend.post.dto.PostCreation;
import com.project.semipermbackend.post.dto.PostViewDto;
import com.project.semipermbackend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/community")
@RestController
public class PostController {
    private final PostService postService;

    // 게시글 등록
    // request
    // response : post_id

    @PostMapping("/post")
    public ResponseEntity<ApiResultDto<PostCreation.ResponseDto>> postCreation(@Valid @RequestBody PostCreation.RequestDto postCreation) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("memberID: "+ Long.valueOf(principal.getUsername()));
        PostCreation.ResponseDto responseDto = postService.create(1L, postCreation);

        return new ResponseEntity<>(ApiResultDto.success(responseDto), HttpStatus.CREATED);
    }



    // 게시글 조회 (sorting 필드에 따라 정렬) (페이징 처리)
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResultDto<PostViewDto.Response>> postCreation(@PathVariable Long postId) {

        PostViewDto.Response response = postService.getOne(postId);
        return new ResponseEntity<>(ApiResultDto.success(response), HttpStatus.CREATED);

    }

    // 게시글 좋아요


}
