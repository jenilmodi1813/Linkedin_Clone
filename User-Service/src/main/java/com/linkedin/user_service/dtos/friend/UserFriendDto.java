package com.linkedin.user_service.dtos.friend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFriendDto {

    @JsonIgnore
    private Long id;
    private String username;
    @JsonIgnore
    private String email;
    private String firstName;
    private String lastName;
}
