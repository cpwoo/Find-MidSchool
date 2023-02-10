package com.example.findmidschool.school.service;

import com.example.findmidschool.school.entity.School;
import com.example.findmidschool.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolRepositoryService {

    private final SchoolRepository schoolRepository;

    @Transactional
    public void updateAddress(Long id, String address) {

        School entity = schoolRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)) {
            log.error("[SchoolRepositoryService updateAddress] not found id: {}", id);
        }

        entity.changeAddress(address);

    }

    // for test
    public void updateAddressWithoutTransaction(Long id, String address) {

        School entity = schoolRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)) {
            log.error("[SchoolRepositoryService updateAddress] not found id: {}", id);
        }

        entity.changeAddress(address);

    }

}
