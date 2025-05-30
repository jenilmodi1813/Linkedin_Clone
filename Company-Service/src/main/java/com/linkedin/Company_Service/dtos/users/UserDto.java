package com.linkedin.Company_Service.dtos.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {


    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}

