package com.linkedin.Company_Service.services.impl;

import com.linkedin.Company_Service.config.UserServiceClient;
import com.linkedin.Company_Service.dtos.job.applied_job.AppliedJobDto;
import com.linkedin.Company_Service.dtos.job.applied_job.CreateAppliedJobDto;
import com.linkedin.Company_Service.dtos.job.applied_job.ResumeDto;
import com.linkedin.Company_Service.dtos.users.UserFileDto;
import com.linkedin.Company_Service.dtos.users.UserProfileDto;
import com.linkedin.Company_Service.entities.Users.UserFiles;
import com.linkedin.Company_Service.entities.Users.Users;
import com.linkedin.Company_Service.entities.job.AppliedJobs;
import com.linkedin.Company_Service.entities.job.Job;
import com.linkedin.Company_Service.enums.UserFileType;
import com.linkedin.Company_Service.exception.ApiException;
import com.linkedin.Company_Service.repositories.AppliedJobsRepository;
import com.linkedin.Company_Service.repositories.JobRepository;
import com.linkedin.Company_Service.repositories.UserFilesRepository;
import com.linkedin.Company_Service.repositories.UserRepository;
import com.linkedin.Company_Service.services.AppliedJobsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AppliedJobsServiceImpl implements AppliedJobsService {

    @Autowired
    private AppliedJobsRepository appliedJobsRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserFilesRepository userFilesRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Void> createAppliedJob(CreateAppliedJobDto createAppliedJobDto, Long id) {

        Job byId = jobRepository.findById(createAppliedJobDto.getJobId()).orElseThrow(()->new ApiException("Job Not Found", HttpStatus.NOT_FOUND));

        Users users = new Users();
        users.setId(id);

//        UserFileDto userFilesById = userServiceClient.getUserFilesById(createAppliedJobDto.getResumeId());
//        System.out.println(userFilesById.getId());
        UserFiles userFiles = userFilesRepository.findById(createAppliedJobDto.getResumeId()).orElseThrow(()->new ApiException("Resume Not Found ",HttpStatus.NOT_FOUND));
        userFiles.setType(UserFileType.RESUME);

        AppliedJobs appliedJob = new AppliedJobs();
        appliedJob.setJob(byId);
        appliedJob.setAppliedBy(users);
        appliedJob.setResume(userFiles);
        appliedJob.setAppliedAt(new Date());

        appliedJobsRepository.save(appliedJob);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<AppliedJobDto>> getAppliedJobsByJobId(Long id) {
        List<AppliedJobs> byJobId = appliedJobsRepository.findByJobId(id);

        return ResponseEntity.ok().body(byJobId.stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    private AppliedJobDto mapToDto(AppliedJobs job) {

        AppliedJobDto dto = new AppliedJobDto();
        dto.setId(job.getId());
        dto.setAppliedAt(job.getAppliedAt());

        UserFileDto resumeDto = new UserFileDto();
        resumeDto.setLink(job.getResume().getLink());
        resumeDto.setName(job.getResume().getName());

        dto.setResume(resumeDto);
//        System.out.println(job.getAppliedBy().getId());
        UserProfileDto userProfileDto = getUserProfile(job.getAppliedBy().getId());
        if(userProfileDto != null) dto.setAppliedBy(userProfileDto);

        return dto;
    }
    private UserProfileDto getUserProfile(long userId){
        try {
            UserProfileDto body = userServiceClient.getProfile(userId).getBody();
            return body;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
