package com.linkedin.Company_Service.dtos.company.location;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCompanyLocationDto {
    @Size(min = 5, max = 255)
    private String address;

    @Size(max = 20)
    private String zipCode;

    @Size(max = 40)
    private String city;

    @Size(max = 40)
    private String country;
}
