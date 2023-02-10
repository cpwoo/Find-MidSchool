package com.example.findmidschool.school.repository;

import com.example.findmidschool.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
