package com.linkedin.user_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @JsonIgnore
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}

