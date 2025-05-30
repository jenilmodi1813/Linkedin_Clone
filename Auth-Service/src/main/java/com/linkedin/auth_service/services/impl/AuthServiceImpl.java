package com.linkedin.auth_service.services.impl;


import com.linkedin.auth_service.constance.UserRole;
import com.linkedin.auth_service.dtos.LoginRequestDto;
import com.linkedin.auth_service.dtos.RegisterRequestDto;
import com.linkedin.auth_service.entities.Role;
import com.linkedin.auth_service.entities.Users;
import com.linkedin.auth_service.exception.ApiException;
import com.linkedin.auth_service.repositories.RoleRepository;
import com.linkedin.auth_service.repositories.UserRepository;
import com.linkedin.auth_service.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final String USER_NAME_ALREADY_EXISTS = "username already exists";
    private final String USER_ALREADY_EXISTS = "user already exists";
    private final String EMAIL_ALREADY_EXISTS = "email already exists";
    private final String USER_EMAIL_DOES_NOT_EXIST = "user email does not exist";
    private final String USER_PASSWORD_DOES_NOT_EXIST = "user password does not exist";
    private final String LOGIN_PLEASE = "User not Login";
    private final String INVALID_CREDENTIALS = "Invalid Password";
    private final String ROLE_NOT_FOUND="Default USER role not found";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public ResponseEntity<String> register(RegisterRequestDto request) {
        Optional<Users> user = userRepository.findByUsernameAndEmail(request.getUsername(),request.getEmail());
        if(user.isPresent()) throw new ApiException(HttpStatus.BAD_REQUEST,USER_ALREADY_EXISTS);

        Role defaultRole = roleRepository.findByName(UserRole.USER.withPrefix())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,ROLE_NOT_FOUND));

        Users saveUsers = new Users();
        saveUsers.setUsername(request.getUsername());
        saveUsers.setEmail(request.getEmail());
        saveUsers.setPassword(request.getPassword());
        saveUsers.setFirstname(request.getFirstName());
        saveUsers.setLastname(request.getLastName());
        saveUsers.setActive(false);
        saveUsers.setCreatedAt(LocalDateTime.now().toString());
        saveUsers.setRoles(new HashSet<>(Set.of(defaultRole)));

        userRepository.save(saveUsers);
        return ResponseEntity.ok().body("User registered ");
    }

    @Override
    public ResponseEntity<String> login(LoginRequestDto loginRequestDto) {

        Users byEmailUser = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,USER_EMAIL_DOES_NOT_EXIST));
        if(!byEmailUser.getPassword().equals(loginRequestDto.getPassword())){
            throw new ApiException(HttpStatus.BAD_REQUEST,INVALID_CREDENTIALS);
        }
        byEmailUser.setActive(true);
        userRepository.save(byEmailUser);
        return ResponseEntity.ok().body("Login Successfull ");
    }

    @Override
    public ResponseEntity<String> logout(String email) {
        // on logout isactive should not be false
        Users byEmailUser = userRepository.findByEmail(email).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,USER_EMAIL_DOES_NOT_EXIST));
        if(!byEmailUser.isActive()){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,LOGIN_PLEASE);
        }
        byEmailUser.setActive(false);
        userRepository.save(byEmailUser);
        return ResponseEntity.ok().body("Logout Successfull ");
    }

    @Override
    public boolean isLoggedIn(Long userId) {
        // is active false means user deleted his acc from platform
        return userRepository.findById(userId)
                .map(Users::isActive)
                .orElse(false);
    }

    @Override
    public ResponseEntity<String> registerAdmin(RegisterRequestDto request) {
        Optional<Users> user = userRepository.findByUsernameAndEmail(request.getUsername(),request.getEmail());
        if(user.isPresent()) throw new ApiException(HttpStatus.BAD_REQUEST,USER_ALREADY_EXISTS);

        Role defaultRole = roleRepository.findByName(UserRole.ADMIN.withPrefix())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,ROLE_NOT_FOUND));

        Users saveUsers = new Users();
        saveUsers.setUsername(request.getUsername());
        saveUsers.setEmail(request.getEmail());
        saveUsers.setPassword(request.getPassword());
        saveUsers.setFirstname(request.getFirstName());
        saveUsers.setLastname(request.getLastName());
        saveUsers.setActive(false);
        saveUsers.setCreatedAt(LocalDateTime.now().toString());
        saveUsers.setRoles(new HashSet<>(Set.of(defaultRole)));

        userRepository.save(saveUsers);
        return ResponseEntity.ok().body("Admin registered ");
    }
}
