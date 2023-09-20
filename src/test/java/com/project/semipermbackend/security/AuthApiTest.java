package com.project.semipermbackend.security;

import com.project.semipermbackend.config.asciidocs.AbstractRestDocsTestSupport;
import com.project.semipermbackend.member.controller.MemberController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@WebAppConfiguration
// TODO Security Test 알아봐야함.
@WebMvcTest() // ControllerTest에서 확장함. 근데 모든 컨트롤러 테스트에서 그럼 받는거네? 여기선 MemberController.class만 필요할 수 있는데?
public class AuthApiTest extends AbstractRestDocsTestSupport {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("네이버 소셜 로그인 인증(회원 아닌 경우) - 성공")
    void naverLogin_oauth_success() throws Exception {

        // 매번 바꿔줘야해..???
//        String accessToken = "jWthpdu1HCV2ObfGZ5UuG9zSZajL1CZE9-7sxFmhYYurD7xtEDhvCM0SbdFEAuVPjT28fgorDKYAAAGJzoCZ2A";

        // 헤더에 Authorization 필드 존재
        mockMvc.perform(get("/oauth2/login/naver")
                                .contentType(MediaType.APPLICATION_JSON)
//                                .header("Authentication", accessToken)
//                                .header("Authentication", "Bearer" + accessToken)
                        .header("Authentication", "Bearer access-token"))
                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.data.accessToken").exists())
//                .andExpect(jsonPath("$.data.refreshToken").exists())
                .andDo(document("auth-login",
                        requestHeaders(
                                headerWithName("Authentication").description("소셜로그인으로부터 발급된 access token 값")
//                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT access token")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("accountId").description("계정 정보 저장 후 발급된 계정 시퀀스(pk) 값"),
                                fieldWithPath("randomNickname").description("랜덤 닉네임값")
                        )
                ));
    }

    @Disabled
    @Test
    @DisplayName("소셜 로그인(회원인 경우) - 성공")
    void kakaoLogin_success() throws Exception {

        // 매번 바꿔줘야해..???
        String accessToken = "jWthpdu1HCV2ObfGZ5UuG9zSZajL1CZE9-7sxFmhYYurD7xtEDhvCM0SbdFEAuVPjT28fgorDKYAAAGJzoCZ2A";

        // 헤더에 Authorization 필드 존재
        mockMvc.perform(get("/login")
                .header("Authentication", "Bearer" + accessToken)
                .accept(MediaType.APPLICATION_JSON)
//                .andDo(print())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists())
                .andDo(document("auth-login",
                        requestHeaders(
                                headerWithName("Authentication").description("소셜로그인으로부터 받은 access token 값")
//                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT access token")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("accessToken").description("JWT Access Token"),
                                fieldWithPath("refreshToken").description("JWT Refresh Token")
                        )
                ));
    }
}
