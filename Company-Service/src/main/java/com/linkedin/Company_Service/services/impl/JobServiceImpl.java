package com.linkedin.Company_Service.services.impl;

import com.linkedin.Company_Service.dtos.job.CreateJobDto;
import com.linkedin.Company_Service.dtos.job.JobDto;
import com.linkedin.Company_Service.dtos.job.UpdateJobDto;
import com.linkedin.Company_Service.entities.Category;
import com.linkedin.Company_Service.entities.Users.Users;
import com.linkedin.Company_Service.entities.company.Company;
import com.linkedin.Company_Service.entities.job.Job;
import com.linkedin.Company_Service.enums.JobType;
import com.linkedin.Company_Service.exception.ApiException;
import com.linkedin.Company_Service.repositories.CategoryRepository;
import com.linkedin.Company_Service.repositories.CompanyRepository;
import com.linkedin.Company_Service.repositories.JobRepository;
import com.linkedin.Company_Service.services.JobService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<Void> createJob(CreateJobDto createJobDto, Long id) {

        JobType jobType = returnJobTypeOrThrowError(createJobDto.getType());

        Company company = returnCompanyOrThrowError(createJobDto.getCompanyId());

        Category category = returnCategoryOrThrowError(createJobDto.getCategoryId());

        Users users = new Users();
        users.setId(id);

        Job job = new Job();
        job.setCountry(createJobDto.getCountry());
        job.setCity(createJobDto.getCity());
        job.setCompany(company);
        job.setPosition(createJobDto.getPosition());
        job.setDescription(createJobDto.getDescription());
        job.setType(jobType);
        job.setPostedAt(new Date());
        job.setPostedBy(users);
        job.setCategory(category);

        jobRepository.save(job);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateJob(UpdateJobDto updateJobDto, Long id) {
        Job byId = jobRepository.findById(id).orElseThrow(() -> new ApiException("Job with id " + id + " was not found",HttpStatus.NOT_FOUND));

        if(byId.getType() != null){
            JobType jobType = returnJobTypeOrThrowError(updateJobDto.getType());
            byId.setType(jobType);
        }
        if(byId.getCategory() != null){
            Category category = returnCategoryOrThrowError(updateJobDto.getCategoryId());
            byId.setCategory(category);
        }
        if(updateJobDto.getPosition() != null) byId.setPosition(updateJobDto.getPosition());
        if(updateJobDto.getDescription() != null) byId.setDescription(updateJobDto.getDescription());
        if(updateJobDto.getCountry() != null) byId.setDescription(updateJobDto.getDescription());
        if(updateJobDto.getCity() != null) byId.setCity(updateJobDto.getCity());
        jobRepository.save(byId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteJobById(Long id) {
        Job byId = jobRepository.findById(id).orElseThrow(() -> new ApiException("Job with id " + id + " was not found",HttpStatus.NOT_FOUND));

        jobRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<JobDto>> getAllJobs() {

        List<Job> all = jobRepository.findAll();

        List<JobDto> dto = all.stream().map(job -> mapToJobDto(job)).collect(Collectors.toList());

        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<List<JobDto>> getJobsByCategory(Long categoryId) {
        Category category = returnCategoryOrThrowError(categoryId);
        List<Job> byCategoryId = jobRepository.findByCategoryId(category.getId());

        List<JobDto> dto = byCategoryId.stream().map(job -> mapToJobDto(job)).collect(Collectors.toList());
        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<List<JobDto>> getJobsByCompanyId(Long companyId) {

        Company company = returnCompanyOrThrowError(companyId);
        List<Job> byCompanyId = jobRepository.findByCompanyId(company.getId());

        List<JobDto> dto = byCompanyId.stream().map(job -> mapToJobDto(job)).collect(Collectors.toList());
        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<JobDto> getJobById(Long id) {
        Job byId = jobRepository.findById(id).orElseThrow(() -> new ApiException("Job with id " + id + " was not found",HttpStatus.NOT_FOUND));

        return ResponseEntity.ok().body(mapToJobDto(byId));
    }


    private JobDto mapToJobDto(Job job) {
        Company company = job.getCompany();
        Category category = job.getCategory();

        JobDto dto = new JobDto();
        dto.setId(job.getId());
        dto.setDescription(job.getDescription());
        dto.setCountry(job.getCountry());
        dto.setCity(job.getCity());
//        dto.setCompany(company);
        dto.setCategory(category.getName());
        dto.setType(job.getType().toString());
        dto.setPosition(job.getPosition());
        dto.setPostedAt(job.getPostedAt());

        return dto;
    }

    private JobType returnJobTypeOrThrowError(String type) {

        JobType jobType;
        try {
            jobType = JobType.valueOf(type.toUpperCase());
        }
        catch (InvalidMediaTypeException e){
            e.printStackTrace();
            throw new ApiException("Invalid Job Type. Only " + JobType.ONSITE + ", " + JobType.HYBRID + ", " + JobType.REMOTE + " are supported",HttpStatus.NOT_FOUND);
        }
        return jobType;
    }

    private Company returnCompanyOrThrowError(long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ApiException("Company with id " + companyId + " was not found",HttpStatus.NOT_FOUND));
    }

    private Category returnCategoryOrThrowError(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiException("Category with id " + categoryId + " was not found",HttpStatus.NOT_FOUND));
    }
}
