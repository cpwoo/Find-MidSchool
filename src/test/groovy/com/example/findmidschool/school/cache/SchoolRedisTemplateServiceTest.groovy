package com.example.findmidschool.school.cache

import com.example.findmidschool.AbstractIntegrationContainerBaseTest
import com.example.findmidschool.school.dto.SchoolDto
import org.springframework.beans.factory.annotation.Autowired

class SchoolRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private SchoolRedisTemplateService sut

    def setup() {
        sut.findAll()
                .forEach(dto -> {
                    sut.delete(dto.getId())
                })
    }

    def "save success"() {

        given:
        String schoolName = "name"
        String schoolAddress = "address"
        SchoolDto dto = SchoolDto.builder()
                .id(1L)
                .schoolName(schoolName)
                .schoolAddress(schoolAddress)
                .build()

        when:
        sut.save(dto)
        List<SchoolDto> result = sut.findAll()

        then:
        result.size() == 1
        result.get(0).id == 1L
        result.get(0).schoolName == schoolName
        result.get(0).schoolAddress == schoolAddress

    }

    def "success fail"() {

        given:
        SchoolDto dto = SchoolDto.builder().build()

        when:
        sut.save(dto)
        List<SchoolDto> result = sut.findAll()

        then:
        result.size() == 0

    }

    def "delete"() {

        given:
        String schoolName = "name"
        String schoolAddress = "address"
        SchoolDto dto = SchoolDto.builder()
                .id(1L)
                .schoolName(schoolName)
                .schoolAddress(schoolAddress)
                .build()

        when:
        sut.save(dto)
        sut.delete(dto.getId())
        def result = sut.findAll()

        then:
        result.size() == 0

    }

}
