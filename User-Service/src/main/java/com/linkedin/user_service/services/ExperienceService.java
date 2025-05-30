package com.linkedin.user_service.services;

import com.linkedin.user_service.dtos.experience.CreateExperienceDto;
import com.linkedin.user_service.dtos.experience.ExperienceDto;
import com.linkedin.user_service.dtos.experience.UpdateExperienceDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface ExperienceService {
    ResponseEntity<Void> createExperience(@Valid CreateExperienceDto createExperienceDto, Long id);

    ExperienceDto getExperienceById(Long id);

    ResponseEntity<Void> deleteExperienceById(Long exid,Long id);

    ResponseEntity<Void> updateExperienceDto(@Valid UpdateExperienceDto updateExperienceDto, Long exId, Long id);
}
