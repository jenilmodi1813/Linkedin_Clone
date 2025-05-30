package com.linkedin.auth_service.controller;

import com.linkedin.auth_service.dtos.LoginRequestDto;
import com.linkedin.auth_service.dtos.RegisterRequestDto;
import com.linkedin.auth_service.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDto request) {
        return authService.register(request);
    }


    // only super admin can create admin user.
    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterRequestDto request) {
        return authService.registerAdmin(request);
    }
//
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }
//
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String email) {
        return authService.logout(email);
    }

    @GetMapping("is-logged-in/{userId}")
    public boolean isLoggedIn(@PathVariable Long userId) {
        return authService.isLoggedIn(userId);
    }
}
