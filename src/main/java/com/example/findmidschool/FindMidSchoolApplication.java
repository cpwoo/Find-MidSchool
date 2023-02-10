package com.example.findmidschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FindMidSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(FindMidSchoolApplication.class, args);
    }

}
