package com.example.findmidschool.school.service

import com.example.findmidschool.school.cache.SchoolRedisTemplateService
import com.example.findmidschool.school.entity.School
import spock.lang.Specification

class SchoolSearchServiceTest extends Specification {

    private SchoolSearchService sut

    private SchoolRepositoryService schoolRepositoryService = Mock()
    private SchoolRedisTemplateService schoolRedisTemplateService = Mock()

    private List<School> schoolList

    def setup() {

        sut = new SchoolSearchService(schoolRepositoryService, schoolRedisTemplateService)

        schoolList = new ArrayList<>()
        schoolList.addAll(
                School.builder()
                        .id(1L)
                        .schoolName("갈뫼중학교")
                        .schoolAddress("주소1")
                        .latitude(37.37722381969726)
                        .longitude(126.97475443776447)
                        .build(),
                School.builder()
                        .id(2L)
                        .schoolName("고천중학교")
                        .schoolAddress("주소2")
                        .latitude(37.35299841024769)
                        .longitude(126.98248277347724)
                        .build()
        )

    }

    def "Redis 장애시 DB 를 이용하여 학교 데이터 조회"() {

        when:
        schoolRedisTemplateService.findAll() >> []
        schoolRepositoryService.findAll() >> schoolList

        def result = sut.searchSchoolDtoList()

        then:
        result.size() == 2

    }

}
