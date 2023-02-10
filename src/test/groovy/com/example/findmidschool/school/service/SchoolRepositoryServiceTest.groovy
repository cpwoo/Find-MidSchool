package com.example.findmidschool.school.service

import com.example.findmidschool.AbstractIntegrationContainerBaseTest
import com.example.findmidschool.school.entity.School
import com.example.findmidschool.school.repository.SchoolRepository
import org.springframework.beans.factory.annotation.Autowired

class SchoolRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private SchoolRepositoryService sut

    @Autowired
    private SchoolRepository schoolRepository

    def setup() {
        schoolRepository.deleteAll()
    }

    def "schoolRepository update - dirty checking success"() {

        given:
        String inputAddress = "의왕시 오전동"
        String modifiedAddress = "의왕시 고천동"
        String name = "모락중학교"

        def school = School.builder()
                .schoolAddress(inputAddress)
                .schoolName(name)
                .build()

        when:
        def entity = schoolRepository.save(school)
        sut.updateAddress(entity.getId(), modifiedAddress)

        def result = schoolRepository.findAll()

        then:
        result.get(0).getSchoolAddress() == modifiedAddress

    }

    def "schoolRepository update - dirty checking fail"() {

        given:
        String inputAddress = "의왕시 오전동"
        String modifiedAddress = "의왕시 고천동"
        String name = "모락중학교"

        def school = School.builder()
                .schoolAddress(inputAddress)
                .schoolName(name)
                .build()

        when:
        def entity = schoolRepository.save(school)
        sut.updateAddressWithoutTransaction(entity.getId(), modifiedAddress)

        def result = schoolRepository.findAll()

        then:
        result.get(0).getSchoolAddress() == inputAddress

    }

}
