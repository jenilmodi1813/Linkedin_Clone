package com.linkedin.Company_Service.dtos.job;


import com.linkedin.Company_Service.dtos.company.CompanyDto;
import lombok.Data;

import java.util.Date;

@Data
public class JobDto {
    private long id;
//    private CompanyDto company;
    private String category;
    private String description;
    private String position;
    private String type;
    private String city;
    private String country;
    private Date postedAt;
}
