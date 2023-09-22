package com.project.semipermbackend.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.semipermbackend.post.dto.PostCreation;
import com.project.semipermbackend.post.service.PostService;
import com.project.semipermbackend.domain.code.PostCategory;
import com.project.semipermbackend.domain.code.SurgeryCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService communityService;


    @Test
    @DisplayName("게시글 생성 API")
    public void postCreation() throws Exception {
        // given
        PostCreation.RequestDto requestDto = PostCreation.RequestDto.builder()
                .postCategory(PostCategory.INFO)
                .surgeryCategory(SurgeryCategory.EYEBROW_TATOO)
                .title("정보 공유해요~!")
                .content("강남역 2번 출구에 있는 가게 '눈썹신' 여자 눈썹 문신 잘하네요.")
                .build();
        PostCreation.ResponseDto responseDto = new PostCreation.ResponseDto(1L);
        when(communityService.create(anyLong(), requestDto)).thenReturn(responseDto);

        // when
        mockMvc.perform(
                        post("/post")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto))
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(true))
                        .andDo(
                                document("create-post",
                                        requestFields(
                                                fieldWithPath("title").description("게시글 제목"),
                                                fieldWithPath("content").description("게시글 내용"),
                                                fieldWithPath("postCategory").description("게시글 카테고리"),
                                                fieldWithPath("surgeryCategory").description("시술 카테고리")

                                        ),
                                        responseFields(

                                        )

                        )
        );
    }

}