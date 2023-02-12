package com.example.findmidschool.direction.controller

import com.example.findmidschool.direction.dto.OutputDto
import com.example.findmidschool.school.service.SchoolRecommendationService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class FormControllerTest extends Specification {

    private MockMvc mockMvc
    private SchoolRecommendationService schoolRecommendationService = Mock()
    private List<OutputDto> outputDtoList

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FormController(schoolRecommendationService))
                .build()

        outputDtoList = new ArrayList<>()
        outputDtoList.addAll(
                OutputDto.builder().schoolName("학교1").build(),
                OutputDto.builder().schoolName("학교2").build()
        )
    }

    def "GET /"() {
        expect:
        mockMvc.perform(get("/"))
                .andExpect(handler().handlerType(FormController.class))
                .andExpect(handler().methodName("main"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andDo(log())
    }

    def "POST /search"() {
        given:
        String inputAddress = "경기 의왕시 오전동"

        when:
        def resultActions = mockMvc.perform(post("/search")
                .param("address", inputAddress))

        then:
        1 * schoolRecommendationService.recommendSchoolList(argument -> {
            assert argument == inputAddress // mock 객체의 argument 검증
        }) >> outputDtoList

        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("output"))
                .andExpect(model().attributeExists("outputFormList")) // model 에 outputFormList 라는 key 가 존재하는지 확인
                .andExpect(model().attribute("outputFormList", outputDtoList))
                .andDo(print())
    }

}
