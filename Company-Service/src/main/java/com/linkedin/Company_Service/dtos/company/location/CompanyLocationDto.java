package com.linkedin.Company_Service.dtos.company.location;


import lombok.Data;

@Data
public class CompanyLocationDto {
    private long id;
    private String address;
    private String zipCode;
    private String city;
    private String country;
}
