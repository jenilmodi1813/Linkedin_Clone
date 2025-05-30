package com.linkedin.user_service.services.impl;

import com.linkedin.user_service.dtos.experience.CreateExperienceDto;
import com.linkedin.user_service.dtos.experience.ExperienceDto;
import com.linkedin.user_service.dtos.experience.UpdateExperienceDto;
import com.linkedin.user_service.entities.Experience;
import com.linkedin.user_service.entities.Users;
import com.linkedin.user_service.exception.ApiException;
import com.linkedin.user_service.repositories.ExperienceRepository;
import com.linkedin.user_service.repositories.UserRepository;
import com.linkedin.user_service.services.ExperienceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    // put this message in single file
    private final String USER_DOES_NOT_EXIST = "user does not exist";
    private final String INVALID_INPUT = "INVALID INPUT";
    private final String EXPERIENCE_DOES_NOT_EXIST = "experience not exist";

    @Override
    public ResponseEntity<Void> createExperience(CreateExperienceDto createExperienceDto, Long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));

        // create common method for response setter/getter
        Experience experience = new Experience();
        experience.setUser(user);
        experience.setDescription(createExperienceDto.getDescription());
        experience.setExperienceYear(createExperienceDto.getExperienceInYears());
        experience.setCompany(createExperienceDto.getCompany());
        experience.setPosition(createExperienceDto.getPosition());
        experience.setStartDate(createExperienceDto.getStartDate());
        experience.setEndDate(createExperienceDto.getEndDate());

        experienceRepository.save(experience);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ExperienceDto getExperienceById(Long id) {

        Experience experience = experienceRepository.findById(id).orElseThrow(() -> new ApiException(EXPERIENCE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));

        return mapToExperienceDto(experience);
    }

    @Override
    public ResponseEntity<Void> deleteExperienceById(Long exid, Long id) {
        //no need to check it exist or not
        Experience experience = experienceRepository.findById(exid).orElseThrow(() -> new ApiException(EXPERIENCE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        experienceRepository.deleteByIdAndUserId(exid, id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateExperienceDto(UpdateExperienceDto updateExperienceDto, Long exId, Long id) {
        Experience experience = experienceRepository.findByUserIdAndId(id, exId).orElseThrow(()->new ApiException(INVALID_INPUT,HttpStatus.NOT_FOUND));

        if(updateExperienceDto.getExperienceInYears() != null) experience.setExperienceYear(updateExperienceDto.getExperienceInYears());
        if(updateExperienceDto.getCompany() != null) experience.setCompany(updateExperienceDto.getCompany());
        if(updateExperienceDto.getDescription() != null) experience.setDescription(updateExperienceDto.getDescription());
        if(updateExperienceDto.getPosition() != null) experience.setPosition(updateExperienceDto.getPosition());
        if(updateExperienceDto.getStartDate() != null) experience.setStartDate(updateExperienceDto.getStartDate());
        if(updateExperienceDto.getEndDate() != null) experience.setEndDate(updateExperienceDto.getEndDate());

        experienceRepository.save(experience);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    private ExperienceDto mapToExperienceDto(Experience experience) {
        ExperienceDto dto = new ExperienceDto();
        dto.setId(experience.getId());
        dto.setExperienceInYears(experience.getExperienceYear());
        dto.setStartDate(experience.getStartDate());
        dto.setEndDate(experience.getEndDate());
        dto.setDescription(experience.getDescription());
        dto.setCompany(experience.getCompany());
        dto.setPosition(experience.getPosition());
        dto.setUserId(experience.getUser().getId());

        return dto;
    }
}
