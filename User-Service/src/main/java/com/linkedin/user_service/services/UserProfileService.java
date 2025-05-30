package com.linkedin.user_service.services;

import com.linkedin.user_service.dtos.UpdateUserInfoRequest;
import com.linkedin.user_service.dtos.UserProfileDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UserProfileService {
    ResponseEntity<UserProfileDto> getProfile(Long id);

    ResponseEntity<Void> addProfile(@Valid UserProfileDto userProfileDto,Long id);

    UserProfileDto updateById(@Valid Long id, UpdateUserInfoRequest updateUserInfoRequest);
}
