package com.project.semipermbackend.member.controller;

import com.project.semipermbackend.member.dto.MemberCreation;
import com.project.semipermbackend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok().build();
    }

    /**
     * 회원 수정
     * @return
     */
    /*@PutMapping("/member")
    public ResponseEntity<ApiResultDto<MemberUpdeate.ResponsDto>> memberUpdate() {

    }*/

}
