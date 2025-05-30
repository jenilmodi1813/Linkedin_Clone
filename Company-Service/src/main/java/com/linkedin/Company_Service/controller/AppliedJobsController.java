package com.linkedin.Company_Service.controller;

import com.linkedin.Company_Service.dtos.job.applied_job.AppliedJobDto;
import com.linkedin.Company_Service.dtos.job.applied_job.CreateAppliedJobDto;
import com.linkedin.Company_Service.services.AppliedJobsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jobs/applied")
public class AppliedJobsController {

    @Autowired
    private AppliedJobsService appliedJobsService;

    @PostMapping("")
    public ResponseEntity<Void> createAppliedJob(@Valid @RequestBody CreateAppliedJobDto createAppliedJobDto, @RequestParam Long id){
        return appliedJobsService.createAppliedJob(createAppliedJobDto,id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<AppliedJobDto>> getAppliedJobsByJobId(@PathVariable("id") Long id) {
        return appliedJobsService.getAppliedJobsByJobId(id);
    }
}
