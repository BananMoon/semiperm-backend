package com.project.semipermbackend.auth.entity;

import lombok.Getter;

@Getter
public class NaverResponseDto {

    String resultcode;
    String message;
    Object response;
//    String profileImageUrl;
//    String nickName;    // social type마다 주는 곳 다를 듯.
}
