package com.linkedin.user_service.services;

import com.linkedin.user_service.dtos.UpdateUserRequest;
import com.linkedin.user_service.dtos.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserDto> getUserById(Long id);

    ResponseEntity<Void> updateUser(String email, UpdateUserRequest updateUserRequest);
}
