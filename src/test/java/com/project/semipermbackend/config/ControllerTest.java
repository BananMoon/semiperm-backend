package com.project.semipermbackend.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.semipermbackend.member.controller.MemberController;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 역할 : 모든 도메인의 컨트롤러를 테스트하는데 사용되는 필드들을 관리한다.
 * 작성자 :
 * 작성일자 :
 */
@Disabled
@WebMvcTest({
        MemberController.class
//        , AccountController.class
})
public abstract class ControllerTest {

//    protected String createJson(Object dto) throws JsonProcessingException {
//        return objectMapper.writeValueAsString(dto);
//    }
}
