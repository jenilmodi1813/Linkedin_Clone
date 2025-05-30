package com.linkedin.auth_service.services;

import com.linkedin.auth_service.dtos.LoginRequestDto;
import com.linkedin.auth_service.dtos.RegisterRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<String> register(RegisterRequestDto request);

    ResponseEntity<String> login(LoginRequestDto loginRequestDto);

    ResponseEntity<String> logout(String email);

    boolean isLoggedIn(Long userId);

    ResponseEntity<String> registerAdmin(@Valid RegisterRequestDto request);
}
