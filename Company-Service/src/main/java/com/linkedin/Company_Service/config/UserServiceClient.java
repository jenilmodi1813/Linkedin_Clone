package com.linkedin.Company_Service.config;

import com.linkedin.Company_Service.dtos.users.UserDto;
import com.linkedin.Company_Service.dtos.users.UserFileDto;
import com.linkedin.Company_Service.dtos.users.UserProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE",url = "http://localhost:8081")
public interface UserServiceClient {

    @GetMapping("/User/getUserById/{id}")
    UserDto getUserById(@PathVariable Long id);

    @GetMapping("users/files/{id}")
    UserFileDto getUserFilesById(@PathVariable Long id );

    @GetMapping("/User/profile/{id}")
    ResponseEntity<UserProfileDto> getProfile(@PathVariable Long id);
}
