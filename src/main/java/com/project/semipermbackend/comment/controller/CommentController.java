package com.project.semipermbackend.comment.controller;

import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.comment.dto.CommentCreationDto;
import com.project.semipermbackend.comment.dto.CommentFindDto;
import com.project.semipermbackend.comment.dto.CommentUpdateDto;
import com.project.semipermbackend.comment.service.CommentService;
import com.project.semipermbackend.common.dto.ApiResultDto;
import com.project.semipermbackend.common.dto.Pagination;
import com.project.semipermbackend.common.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    // Put 메서드 : @PathVariable 사용 시 400 Bad Request 발생
    @PutMapping("/comment")
    public ResponseEntity<Void> updatePost(/*@PathVariable Long commentId, */@Valid @RequestBody CommentUpdateDto.Request requestDto) {
        commentService.updateOne(requestDto.getCommentId(), requestDto);

        return ResponseEntity.accepted().build();
    }

    // 좋아요
    @PostMapping("/comment/{commentId}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long commentId) {
        commentService.likeOne(commentId);

        return ResponseEntity.ok().build();
    }

    // 내가 쓴 댓글
    @GetMapping("/my-comments")
    public ResponseEntity<ApiResultDto<Pagination<CommentFindDto.Response>>> getMyComments(
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "perSize", defaultValue = "10", required = false) Integer perSize) {

        Long memberId = JwtTokenProvider.getMemberIdFromContext();

        Page<CommentFindDto.Response> pageMyComments = commentService.findMyComments(page-1, perSize, memberId);
        return new ResponseEntity<>(ApiResultDto.success(PaginationUtil.pageToPagination(pageMyComments)), HttpStatus.FOUND);
    }

    // 삭제
    // 답글들은 삭제되지 않음. Use_yn = false 처리
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteOne(commentId);
        return ResponseEntity.ok().build();
    }
}