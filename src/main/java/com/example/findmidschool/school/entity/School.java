package com.example.findmidschool.school.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.findmidschool.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "school")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class School extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schoolName;
    private String schoolAddress;
    private double latitude;
    private double longitude;

    public void changeAddress(String address) {
        this.schoolAddress = address;
    }

}
