package com.example.findmidschool.direction.entity;

import com.example.findmidschool.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "direction")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Direction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    private String targetSchoolName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    private double distance;

}
