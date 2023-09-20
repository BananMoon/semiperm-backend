package com.project.semipermbackend.config.asciidocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.semipermbackend.config.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

//@Disabled
@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
public abstract class AbstractRestDocsTestSupport {

    @Autowired protected ObjectMapper objectMapper;
    @Autowired protected MockMvc mockMvc;

    @Autowired
    protected RestDocumentationResultHandler restDocs;  // Restful API를 문서화하기 위한 결과 핸들러(Result handler)

    @BeforeEach
    void setUp(final WebApplicationContext waContext, final RestDocumentationContextProvider restDocsProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(waContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocsProvider))   // restdocs 설정 주입
                .alwaysDo(MockMvcResultHandlers.print())    // RestDocsConfig에서 설정한 것 적용
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

}
