package com.example.findmidschool.school.service;

import com.example.findmidschool.school.dto.SchoolDto;
import com.example.findmidschool.school.entity.School;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolSearchService {

    private final SchoolRepositoryService schoolRepositoryService;

    public List<SchoolDto> searchSchoolDtoList() {

        return schoolRepositoryService.findAll()
                .stream()
                .map(this::convertToSchoolDto)
                .collect(Collectors.toList());

    }

    private SchoolDto convertToSchoolDto(School school) {

        return SchoolDto.builder()
                .id(school.getId())
                .schoolAddress(school.getSchoolAddress())
                .schoolName(school.getSchoolName())
                .latitude(school.getLatitude())
                .longitude(school.getLongitude())
                .build();

    }

}
