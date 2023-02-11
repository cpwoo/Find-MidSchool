package com.example.findmidschool.api.service

import com.example.findmidschool.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService sut

    def "address 파라미터 값이 null 이면, requestAddressSearch 메소드는 null 을 리턴한다."() {

        given:
        String address = null

        when:
        def result = sut.requestAddressSearch(address)

        then:
        result == null

    }

    def "주소값이 valid 하다면, requestAddressSearch 메소드는 정상적으로 document 를 반환한다."() {

        given:
        def address = "의왕시 오전동 모락로 110"

        when:
        def result = sut.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null

    }

    def "정상적인 주소를 입력했을 경우, 정상적으로 위도 경도로 반환된다."() {

        given:
        boolean actualResult = false

        when:
        def searchResult = sut.requestAddressSearch(inputAddress)

        then:
        if (searchResult == null) actualResult = false
        else actualResult = searchResult.getDocumentList().size() > 0

        where:
        inputAddress           | expectedResult
        "경기도 의왕시"           | true
        "의왕시 모락로 110"       | true
        "의왕시 모락로"           | true
        "의왕시 모락로 잘못된 주소" | false
        "의왕시 모락로 11111"     | false
        ""                     | false

    }

}
