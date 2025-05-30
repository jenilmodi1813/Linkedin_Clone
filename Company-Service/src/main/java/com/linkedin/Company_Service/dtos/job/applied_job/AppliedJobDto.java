package com.linkedin.Company_Service.dtos.job.applied_job;
import com.linkedin.Company_Service.dtos.users.UserFileDto;
import com.linkedin.Company_Service.dtos.users.UserProfileDto;
import lombok.Data;

import java.util.Date;

@Data
public class AppliedJobDto {
    private long id;
    private UserProfileDto appliedBy;
    private UserFileDto resume;
    private Date appliedAt;
}
