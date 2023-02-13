package com.example.findmidschool.direction.service

import com.example.findmidschool.api.dto.DocumentDto
import com.example.findmidschool.direction.repository.DirectionRepository
import com.example.findmidschool.school.dto.SchoolDto
import com.example.findmidschool.school.service.SchoolSearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {

    private SchoolSearchService schoolSearchService = Mock()
    private DirectionRepository directionRepository = Mock()
    private Base62Service base62Service = Mock()

    private DirectionService directionService = new DirectionService(
            schoolSearchService, directionRepository, base62Service)

    private List<SchoolDto> schoolList

    def setup() {

        schoolList = new ArrayList<>()
        schoolList.addAll(
                SchoolDto.builder()
                        .id(1L)
                        .schoolName("갈뫼중학교")
                        .schoolAddress("주소1")
                        .latitude(37.37722381969726)
                        .longitude(126.97475443776447)
                        .build(),
                SchoolDto.builder()
                        .id(2L)
                        .schoolName("고천중학교")
                        .schoolAddress("주소2")
                        .latitude(37.35299841024769)
                        .longitude(126.98248277347724)
                        .build()
        )

    }

    def "buildDirectionList - 결과 값이 거리 순으로 정렬이 되는가"() {

        given:
        def addressName = "경기 의왕시 보식골로 30"
        double inputLatitude = 37.35968972628792
        double inputLongitude = 126.97418237301221

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        schoolSearchService.searchSchoolDtoList() >> schoolList

        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetSchoolName == "고천중학교"
        results.get(1).targetSchoolName == "갈뫼중학교"

    }

    def "buildDirectionList - 정해진 반경 10 km 내에 검색이 되는지 확인"() {

        given:
        schoolList.add(
                SchoolDto.builder()
                        .id(3L)
                        .schoolName("영일중학교")
                        .schoolAddress("주소3")
                        .latitude(37.24313199087363)
                        .longitude(127.06967403105654)
                        .build())

        def addressName = "경기 의왕시 보식골로 30"
        double inputLatitude = 37.35968972628792
        double inputLongitude = 126.97418237301221

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        schoolSearchService.searchSchoolDtoList() >> schoolList

        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetSchoolName == "고천중학교"
        results.get(1).targetSchoolName == "갈뫼중학교"

    }

}
