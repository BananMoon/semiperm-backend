package com.project.semipermbackend.post.dto;

import lombok.Builder;
import lombok.Getter;

public interface PostViewDto {
    // 회원 닉네임, 좋아요 갯수, 현재 시각 기준 작성 시각, 조회수, 타이틀, 내용, List<댓글>
    @Getter
    @Builder
    class Response {
        String nickname;

        int likeCount;
        int elapsedUploadTime;    // 경과 업로드 시각

        int viewCount;

        String title;

        String content;

//        CommentViewDto.Response comment;

    }
}
