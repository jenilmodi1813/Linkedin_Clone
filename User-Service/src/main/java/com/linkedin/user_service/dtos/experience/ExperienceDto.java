package com.linkedin.user_service.dtos.experience;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {

    @JsonIgnore
    private long id;
    private long userId;
    private String company;
    private String position;
    private String description;
    private BigDecimal experienceInYears;
    private LocalDate startDate;
    private LocalDate endDate;

}
