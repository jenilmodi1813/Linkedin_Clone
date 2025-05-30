package com.linkedin.Company_Service.dtos.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CompanyDto {

    @JsonIgnore
    private long id;
    private long createdBy;
    private String name;
    private String numEmployees;
    private String about;
    private String headLine;
    private String website;
//    private String logo;
}
