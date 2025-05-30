package com.linkedin.Company_Service.dtos.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfileDto {
    @JsonIgnore
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String city;
    private String country;
    private String website;
    private String professionalSummary;
    private String headLine;
    private LocalDate dob;
//    private String logo;
//    private String banner;
}
