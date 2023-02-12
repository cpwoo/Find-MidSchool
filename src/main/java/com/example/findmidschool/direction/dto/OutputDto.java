package com.example.findmidschool.direction.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutputDto {

    private String schoolName;
    private String schoolAddress;
    private String directionUrl;
    private String roadViewUrl;
    private String distance;

}
