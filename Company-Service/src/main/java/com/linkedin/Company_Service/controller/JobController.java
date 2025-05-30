package com.linkedin.Company_Service.controller;

import com.linkedin.Company_Service.dtos.job.CreateJobDto;
import com.linkedin.Company_Service.dtos.job.JobDto;
import com.linkedin.Company_Service.dtos.job.UpdateJobDto;
import com.linkedin.Company_Service.services.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("company/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("")
    public ResponseEntity<Void> createJob(@Valid @RequestBody CreateJobDto createJobDto, @RequestParam Long id){

        return jobService.createJob(createJobDto,id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateJob(@Valid @RequestBody UpdateJobDto updateJobDto, @PathVariable("id") Long id) {
        return jobService.updateJob(updateJobDto, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobById(@PathVariable("id") Long id) {
        return jobService.deleteJobById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> getJobById(@PathVariable("id") Long id) {
        return jobService.getJobById(id);
    }

    @GetMapping("")
    public ResponseEntity<List<JobDto>> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<JobDto>> getJobsByCategory(@PathVariable("id") Long categoryId) {
        return jobService.getJobsByCategory(categoryId);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<JobDto>> getJobsByCompany(@PathVariable("id") Long companyId) {
        return jobService.getJobsByCompanyId(companyId);
    }


}
