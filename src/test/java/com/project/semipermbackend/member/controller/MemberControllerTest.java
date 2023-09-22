package com.project.semipermbackend.member.controller;

import com.project.semipermbackend.common.code.Gender;
import com.project.semipermbackend.common.code.FlagYn;
import com.project.semipermbackend.config.asciidocs.AbstractRestDocsTestSupport;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import com.project.semipermbackend.member.dto.MemberCreation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberController.class)
public class MemberControllerTest extends AbstractRestDocsTestSupport {

    // 소셜 인증 통한 사용자 정보 조회
    /*@Test
    void member_info_from_social_oauth() throws Exception {

        mockMvc.perform(post("oauth2/login/naver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authentication", "Bearer access-token"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-info-from-socialOAuth",
                        requestHeaders(
                                headerWithName("Authentication")
                                        .description("인증이 필요한 요청의 경우 인증 토큰을 포함")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
//                                fieldWithPath("success").description("API 응답 성공 여부"),
                                fieldWithPath("accountId").description("계정 시퀀스")
                        )
                ));

    }*/
    @Test
    @Disabled
    @DisplayName("회원가입 성공")
    void member_join_success() throws Exception {
        MemberCreation.RequestDto requestDto = MemberCreation.RequestDto.builder()
                .accountId(1L)
                .birth(LocalDate.now())
                .isOrderThan14(FlagYn.YES)
                .gender(Gender.WOMAN)
//                .nickname("moonz")
//                .interestingFields(Set.of(SurgeryCategory.SMP.getCode()))
//                .needInformation(Set.of())
                .build();


        mockMvc.perform(post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("accountId").description("계정 시퀀스"),
                                        fieldWithPath("gender").description("성별"),
                                        fieldWithPath("birth").description("사용자 생년월일"),
//                                        fieldWithPath("profileLink").description("사용자 프로필 이미지 링크"),
//                                        fieldWithPath("personalInfoProcessYn").description("개인 정보 처리 방침 동의 여부"),
                                        fieldWithPath("agreeToServiceUsage").description("서비스 이용약관 동의"),
                                        fieldWithPath("accessToken").description("소셜로그인 Access Token"),
                                        fieldWithPath("interestCategories").description("관심사 카테고리"),
                                        fieldWithPath("needInformations").description("필요한 정보")
                                )
                        ));
    }

    /*void social_login_success() {

        List<Category> interestCategories = new ArrayList<>();
        interestCategories.add(Category.SMP);
        SocialLoginRequestDto.RequestDto memberRequest = SocialLoginRequestDto.RequestDto.builder()
                .accessToken("")
                .interestCategories(interestCategories)
                .personalInfoProcessYn(FlagYn.YES)
                .nickname("moonz")
                .gender(Gender.WOMAN)
                .profileImageUrl("https://www.naver.com")
                .tel("01012345678")
                .birth(LocalDate.of(1988, 12, 31))
                .email("moonz@a.com")
                .name("윤지1")
                .build();
    }*/
}