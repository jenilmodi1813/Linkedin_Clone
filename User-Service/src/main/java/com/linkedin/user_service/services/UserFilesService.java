package com.linkedin.user_service.services;

import com.linkedin.user_service.dtos.resume.UserFileDto;
import com.linkedin.user_service.dtos.resume.UserResumeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserFilesService {
    ResponseEntity<Void> uploadFIle(MultipartFile file, String fileType, Long id);

    ResponseEntity<List<UserFileDto>> getAllUserFiles();

    UserFileDto getUserFilesById(Long id);
}
