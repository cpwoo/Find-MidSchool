package com.example.findmidschool.school.repository

import com.example.findmidschool.AbstractIntegrationContainerBaseTest
import com.example.findmidschool.school.entity.School
import org.springframework.beans.factory.annotation.Autowired

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

}
