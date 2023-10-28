package com.project.semipermbackend.member.dto;

import com.project.semipermbackend.common.code.Gender;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageDto {
    // 마이페이지 조회
    private String nickname;
    private String profileImageUrl;

    // 마이페이지 수정
    private LocalDate birth;
    private Gender gender;
    private Set<SurgeryCategory> interestingFields;

}
