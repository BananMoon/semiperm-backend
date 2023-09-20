package com.project.semipermbackend.community.controller;

import com.project.semipermbackend.common.dto.ApiResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/community")
@RestController
public class CommunityController {
    // 게시글 등록
    // request
    // response : post_id
    /*@PostMapping("/post")
    public ResponseEntity<ApiResultDto<PostCreation.ResponseDto>> postCreation(@Valid @RequestBody PostCreation.RequestDto postCreation) {

    }


    // 게시글 조회 (sorting 필드에 따라 정렬) (페이징 처리)
    @GetMapping("/post")
    public ResponseEntity<ApiResultDto<PostView.ResponseDto>> postCreation(@Valid @RequestBody PostView.RequestDto postView) {

    }

    // 댓글 등록
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<ApiResultDto<CommentCreation.ResponseDto>> commentCreation(@Valid @RequestBody CommentCreation.RequestDto commentCreation) {

    }
    // 댓글 조회 (페이징 처리)
    @GetMapping("/post/{postId}/comment")
    public ResponseEntity<ApiResultDto<CommentView.ResponseDto>> commentCreation(@Valid @RequestBody CommentView.RequestDto commentView) {

    }*/

    // 게시글 좋아요


}
