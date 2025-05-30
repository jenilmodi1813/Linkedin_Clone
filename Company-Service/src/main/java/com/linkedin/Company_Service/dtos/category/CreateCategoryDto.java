package com.linkedin.Company_Service.dtos.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryDto {
    @NotEmpty
    @Size(min = 3, max = 255)
    private String name;
}
