package com.linkedin.user_service.dtos.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkedin.user_service.entities.Users;
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
