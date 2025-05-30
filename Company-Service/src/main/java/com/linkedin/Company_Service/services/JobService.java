package com.linkedin.Company_Service.services;

import com.linkedin.Company_Service.dtos.job.CreateJobDto;
import com.linkedin.Company_Service.dtos.job.JobDto;
import com.linkedin.Company_Service.dtos.job.UpdateJobDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobService {
    ResponseEntity<Void> createJob(@Valid CreateJobDto createJobDto, Long id);

    ResponseEntity<Void> updateJob(@Valid UpdateJobDto updateJobDto, Long id);

    ResponseEntity<Void> deleteJobById(Long id);

    ResponseEntity<List<JobDto>> getAllJobs();

    ResponseEntity<List<JobDto>> getJobsByCategory(Long categoryId);

    ResponseEntity<List<JobDto>> getJobsByCompanyId(Long companyId);

    ResponseEntity<JobDto> getJobById(Long id);
}
