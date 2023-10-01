package com.project.semipermbackend.comment.controller;

import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.comment.dto.CommentCreationDto;
import com.project.semipermbackend.comment.service.CommentService;
import com.project.semipermbackend.common.dto.ApiResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/community")
@RestController
public class CommentController {
    private final CommentService commentService;
    // 생성
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<ApiResultDto<CommentCreationDto.Response>> commentCreation(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreationDto.Request commentCreation) {

        Long memberId = JwtTokenProvider.getMemberIdFromContext();
        CommentCreationDto.Response response = commentService.create(memberId, postId, commentCreation);

        return new ResponseEntity<>(ApiResultDto.success(response), HttpStatus.CREATED);
    }


    // 좋아요
    @PostMapping("/comment/{commentId}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long commentId) {
        commentService.likeOne(commentId);

        return ResponseEntity.ok().build();
    }


}
