package com.example.findmidschool.school.cache;

import com.example.findmidschool.school.dto.SchoolDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolRedisTemplateService {

    private static final String CACHE_KEY = "SCHOOL";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(SchoolDto schoolDto) {
        if (Objects.isNull(schoolDto) || Objects.isNull(schoolDto.getId())) {
            log.error("Required Values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY,
                    schoolDto.getId().toString(),
                    serializeSchoolDto(schoolDto));
            log.info("[SchoolRedisTemplateService save success] id: {}", schoolDto.getId());
        } catch (Exception e) {
            log.error("[SchoolRedisTemplateService save fail] {}", e.getMessage());
        }
    }

    public List<SchoolDto> findAll() {

        try {
            List<SchoolDto> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                SchoolDto schoolDto = deserializeSchoolDto(value);
                list.add(schoolDto);
            }
            return list;

        } catch (Exception e) {
            log.error("[SchoolRedisTemplateService findAll error] {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[SchoolRedisTemplateService delete]: {}", id);
    }

    public String serializeSchoolDto(SchoolDto schoolDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(schoolDto);
    }

    public SchoolDto deserializeSchoolDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, SchoolDto.class);
    }

}
