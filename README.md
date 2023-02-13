# Find-MidSchool

해당 코드는 [패스트캠퍼스 약국 추천 서비스](https://github.com/WonYong-Jang/Pharmacy-Recommendation)를 참조하여 만들어졌습니다.

[외부 API(카카오 주소 검색 API)](https://developers.kakao.com/docs/latest/ko/local/dev-guide)를 활용했습니다.

추천된 학교 길 안내는 [카카오 지도 및 로드뷰 바로가기 URL](https://apis.map.kakao.com/web/guide/#routeurl)로 제공됩니다.

중학교를 배정할 때 근거리 우선 배정방식을 채택하기 때문에, 실제 서비스가 가능한 프로젝트입니다.

의왕시 내에서 서비스하기 위해 의왕시 내에 있는 중학교로만 데이터를 구성했습니다.

## 요구사항 분석

- 중학교 찾기 서비스 요구사항
  - 해당 서비스로 주소 정보를 입력하여 요청하면 위치 기준에서 가까운 중학교 3곳을 추출한다.
  - 주소는 도로명 주소 또는 지번을 입력하여 요청받는다.
    - 정확한 주소를 입력받기 위해 [카카오 우편번호 서비스](https://postcode.map.daum.net/guide) 사용
  - 주소는 정확한 상세 주소(동, 호수)를 제외한 주소 정보를 이용하여 추천받는다.
    - ex) 경기 의왕시 백운중앙로 46
  - 입력 받은 주소를 위도, 경도로 변환하여 기존 학교 데이터와 비교 및 가까운 학교를 찾는다.
    - 지구는 평면이 아니기 때문에, 구면에서 두 점 사이의 최단 거리 구하는 공식이 필요
    - 두 위 경도 좌표 사이의 거리를 [haversine formula](https://en.wikipedia.org/wiki/Haversine_formula)로 계산
    - 지구가 완전한 구형이 아니므로 아주 조금의 오차가 있다.
  - 입력한 주소 정보에서 정해진 반경(10km) 내에 있는 학교만 추천한다.
  - 추출한 학교 데이터는 길안내 URL 및 로드뷰 URL 로 제공한다.
  - 길안내 URL 은 고객에게 제공되기 때문에 가독성을 위해 shorten url 로 제공된다.
  - shorten url 에 사용되는 key 값은 인코딩하여 제공한다.
    - ex) http://localhost:8080/dir/nqxtX
    - base62 를 통한 인코딩
  - shorten url 의 유효 기간은 30일로 제한된다. 

## School Recommendation Process

## Direction Shorten Url Process

## Tech Stack

- JDK 17
- Spring Boot 2.7.8
- Spring Data JPA
- Gradle
- Handlebars
- Lombok
- Github
- Docker
- Redis
- MariaDB
- Spock
- TestContainers
