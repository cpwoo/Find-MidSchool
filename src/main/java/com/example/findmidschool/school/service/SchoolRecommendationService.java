package com.example.findmidschool.school.service;

import com.example.findmidschool.api.dto.DocumentDto;
import com.example.findmidschool.api.dto.KakaoApiResponseDto;
import com.example.findmidschool.api.service.KakaoAddressSearchService;
import com.example.findmidschool.direction.dto.OutputDto;
import com.example.findmidschool.direction.entity.Direction;
import com.example.findmidschool.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";
    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    public List<OutputDto> recommendSchoolList(String address) {

        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[SchoolRecommendationService recommendSchoolList fail] Input address: {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        List<Direction> directionList = directionService.buildDirectionList(documentDto);

        return directionService.saveAll(directionList).stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());

    }

    private OutputDto convertToOutputDto(Direction direction) {

        String params = String.join(",", direction.getTargetSchoolName(),
                String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));

        String result = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + params).toUriString(); // 학교명이 한글이기 때문에 인코딩해야한다.
        log.info("direction params: {}, url: {}", params, result);

        return OutputDto.builder()
                .schoolName(direction.getTargetSchoolName())
                .schoolAddress(direction.getTargetAddress())
                .directionUrl(result)
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();

    }

}
