package com.linkedin.Company_Service.dtos.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkedin.Company_Service.entities.Users.Users;
import lombok.Data;

@Data
public class UserFileDto {
    @JsonIgnore
    private Long id;
    private String type;
    @JsonIgnore
    private Users user;
    private String name;
    private String link;
}
