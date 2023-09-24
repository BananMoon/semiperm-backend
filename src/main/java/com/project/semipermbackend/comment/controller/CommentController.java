package com.project.semipermbackend.comment.controller;

import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.comment.dto.CommentCreationDto;
import com.project.semipermbackend.comment.service.CommentService;
import com.project.semipermbackend.common.dto.ApiResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController("/community/post")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}/comment")
    public ResponseEntity<ApiResultDto<CommentCreationDto.Response>> commentCreation(
            @PathVariable Long postId,
            @RequestBody CommentCreationDto.Request commentCreation) {

        Long memberId = JwtTokenProvider.getMemberIdFromContext();
        CommentCreationDto.Response response = commentService.create(memberId, postId, commentCreation);

        return new ResponseEntity<>(ApiResultDto.success(response), HttpStatus.CREATED);
    }


}
