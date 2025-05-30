package com.linkedin.Company_Service.services;

import com.linkedin.Company_Service.dtos.job.applied_job.AppliedJobDto;
import com.linkedin.Company_Service.dtos.job.applied_job.CreateAppliedJobDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AppliedJobsService {
    ResponseEntity<Void> createAppliedJob(@Valid CreateAppliedJobDto createAppliedJobDto,Long id);

    ResponseEntity<List<AppliedJobDto>> getAppliedJobsByJobId(Long id);
}
