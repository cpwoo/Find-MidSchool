package com.example.findmidschool.school.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {

    private Long id;
    private String schoolName;
    private String schoolAddress;
    private double latitude;
    private double longitude;

}
