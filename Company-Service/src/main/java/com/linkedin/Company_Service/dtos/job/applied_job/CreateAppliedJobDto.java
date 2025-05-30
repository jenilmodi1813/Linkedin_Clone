package com.linkedin.Company_Service.dtos.job.applied_job;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAppliedJobDto {

    @NotNull
    private Long resumeId;

    @NotNull
    private Long jobId;

}
