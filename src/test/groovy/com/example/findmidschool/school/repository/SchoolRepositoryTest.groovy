package com.example.findmidschool.school.repository

import com.example.findmidschool.AbstractIntegrationContainerBaseTest
import com.example.findmidschool.school.entity.School
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

class SchoolRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private SchoolRepository schoolRepository

    def setup() {
        schoolRepository.deleteAll()
    }

    def "SchoolRepository save"() {

        given:
        String address = "의왕시 오전동"
        String name = "모락중학교"
        double latitude = 37.37
        double longitude = 126.97

        def school = School.builder()
                .schoolAddress(address)
                .schoolName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = schoolRepository.save(school)

        then:
        school.getSchoolAddress() == address
        school.getSchoolName() == name
        school.getLatitude() == latitude
        school.getLongitude() == longitude

    }

    def "schoolRepository saveAll"() {

        given:
        String address = "의왕시 오전동"
        String name = "모락중학교"
        double latitude = 37.37
        double longitude = 126.97

        def school = School.builder()
                .schoolAddress(address)
                .schoolName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        schoolRepository.saveAll(Arrays.asList(school))
        def result = schoolRepository.findAll()

        then:
        result.size() == 1

    }

    def "BaseTimeEntity 등록"() {

        given:
        LocalDateTime now = LocalDateTime.now()
        String address = "의왕시 오전동"
        String name = "모락중학교"

        def school = School.builder()
                .schoolAddress(address)
                .schoolName(name)
                .build()

        when:
        schoolRepository.save(school)
        def result = schoolRepository.findAll()

        then:
        result.get(0).getCreatedDate().isAfter(now)
        result.get(0).getModifiedDate().isAfter(now)

    }

}
