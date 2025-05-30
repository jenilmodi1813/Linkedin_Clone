package com.linkedin.Company_Service.dtos.job;

import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UpdateJobDto {
    @Size(min = 50)
    private String description;
    @Size(min = 5, max = 100)
    private String position;
    @Size(min = 5, max = 10)
    private String type;
    @Size(min = 3, max = 255)
    private String city;
    @Size(min = 3, max = 255)
    private String country;
    private Long categoryId;
}
