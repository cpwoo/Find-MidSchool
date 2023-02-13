package com.example.findmidschool.school.controller;

import com.example.findmidschool.school.cache.SchoolRedisTemplateService;
import com.example.findmidschool.school.dto.SchoolDto;
import com.example.findmidschool.school.service.SchoolRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolRepositoryService schoolRepositoryService;
    private final SchoolRedisTemplateService schoolRedisTemplateService;

    // 데이터 초기 세팅을 위한 임시 메서드
    @GetMapping("/redis/save")
    public String save() {

        List<SchoolDto> schoolDtoList = schoolRepositoryService.findAll()
                .stream().map(school -> SchoolDto.builder()
                        .id(school.getId())
                        .schoolName(school.getSchoolName())
                        .schoolAddress(school.getSchoolAddress())
                        .latitude(school.getLatitude())
                        .longitude(school.getLongitude())
                        .build())
                .collect(Collectors.toList());

        schoolDtoList.forEach(schoolRedisTemplateService::save);

        return "success";

    }

}
