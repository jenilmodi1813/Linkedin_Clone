package com.linkedin.user_service.services.impl;

import com.linkedin.user_service.dtos.UpdateUserRequest;
import com.linkedin.user_service.dtos.UserDto;
import com.linkedin.user_service.entities.Users;
import com.linkedin.user_service.exception.ApiException;
import com.linkedin.user_service.repositories.UserRepository;
import com.linkedin.user_service.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {


    private final String USER_DOES_NOT_EXIST = "user does not exist";
    private final String USER_ALREADY_EXISTS = "user already exists";

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<UserDto> getUserById(Long id) {
        Users byId = userRepository.findById(id).orElseThrow(()->new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));

        UserDto userDto = new UserDto();
        userDto.setId(byId.getId());
        userDto.setUsername(byId.getUsername());
        userDto.setEmail(byId.getEmail());
        userDto.setFirstName(byId.getFirstname());
        userDto.setLastName(byId.getLastname());

        return ResponseEntity.ok().body(userDto);
    }

    @Override
    public ResponseEntity<Void> updateUser(String email, UpdateUserRequest updateUserRequest) {
        Users users = userRepository.findByEmail(email).orElseThrow(()->new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));

        Optional<Users> user = userRepository.findByUsernameAndEmail(updateUserRequest.getUsername(),updateUserRequest.getEmail());
        if(user.isPresent()) throw new ApiException(USER_ALREADY_EXISTS,HttpStatus.BAD_REQUEST);

        users.setUsername(updateUserRequest.getUsername());
        users.setEmail(updateUserRequest.getEmail());
        users.setPassword(updateUserRequest.getPassword());
        users.setFirstname(updateUserRequest.getFirstName());
        users.setLastname(updateUserRequest.getLastName());
        userRepository.save(users);
        return ResponseEntity.ok().build();
    }
}
