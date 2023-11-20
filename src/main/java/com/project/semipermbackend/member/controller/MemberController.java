package com.project.semipermbackend.member.controller;

import com.project.semipermbackend.auth.jwt.JwtTokenProvider;
import com.project.semipermbackend.common.dto.ApiResultDto;
import com.project.semipermbackend.member.dto.MemberCreation;
import com.project.semipermbackend.member.dto.MyPageDto;
import com.project.semipermbackend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입
     * @param memberCreation
     */
    @PostMapping
    public ResponseEntity<Void> memberJoin(@Valid @RequestBody MemberCreation.RequestDto memberCreation) {

        memberService.join(memberCreation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout() {
        Long accountId = JwtTokenProvider.getAccountIdFromContext();

        memberService.logout(accountId);
        return ResponseEntity.ok().build();
    }

    /**
     * 마이페이지(프로필) 조회
     */
    @GetMapping("/mypage")
    public ResponseEntity<ApiResultDto<MyPageDto>> profileDetails() {
        Long memberId = JwtTokenProvider.getMemberIdFromContext();

        return new ResponseEntity<>(ApiResultDto.success(memberService.findProfile(memberId)), HttpStatus.FOUND);
    }

    /**
     * 마이페이지(프로필) 수정
     * 닉네임
     * 생년월일
     * 성별
     */
    @PutMapping("/mypage/info")
    public ResponseEntity<Void> myProfileUpdate(@Valid @RequestBody MyPageDto mypageDto) {
        memberService.updateMyProfile(JwtTokenProvider.getMemberIdFromContext(), mypageDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 마이페이지(프로필) 수정
     * 관심시술(SurgeryCategory) 2개 항목
     */
    @PutMapping("/mypage/interesting-surgery")
    public ResponseEntity<Void> myInterestingSurgeryUpdate(@Valid @RequestBody MyPageDto mypageDto) {
        memberService.updateMyInterestingSurgery(JwtTokenProvider.getMemberIdFromContext(), mypageDto);
        return ResponseEntity.ok().build();
    }

}
