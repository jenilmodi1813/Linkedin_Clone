package com.linkedin.user_service.services.impl;

import com.linkedin.user_service.config.FileUploaderHelper;
import com.linkedin.user_service.dtos.friend.UserFriendDto;
import com.linkedin.user_service.dtos.resume.UserFileDto;
import com.linkedin.user_service.dtos.resume.UserResumeDto;
import com.linkedin.user_service.entities.UserFiles;
import com.linkedin.user_service.entities.Users;
import com.linkedin.user_service.enums.UserFileType;
import com.linkedin.user_service.exception.ApiException;
import com.linkedin.user_service.repositories.UserFilesRepository;
import com.linkedin.user_service.services.UserFilesService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserFilesServiceImpl implements UserFilesService {

    private final String NOT_UPLOADED = "Something Went Wrong File Not Uplaod ";
    @Autowired
    private UserFilesRepository userFilesRepository;

    @Autowired
    private FileUploaderHelper fileUploaderHelper;

    @Override
    public ResponseEntity<Void> uploadFIle(MultipartFile file, String fileType, Long id) {
        UserFileType userFileType;

        try {
            userFileType = UserFileType.valueOf(fileType.toUpperCase());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ApiException("Invalid File Type. Only " + UserFileType.RESUME + " are supported", HttpStatus.BAD_REQUEST);
        }
        String uploadDir = "C:\\Spring_boot\\New_LinkedIn_clone\\UserFile";
        String fileName = file.getOriginalFilename();
        String fullPath = uploadDir + fileName;

        Users users = new Users();
        users.setId(id);
        boolean b = fileUploaderHelper.uploadFile(file);
        if(!b){
            throw new ApiException(NOT_UPLOADED,HttpStatus.NOT_ACCEPTABLE);
        }
        UserFiles userFiles = new UserFiles();
        userFiles.setType(userFileType);
        userFiles.setLink(fullPath);
        userFiles.setUser(users);
        userFiles.setName(file.getOriginalFilename());

        userFilesRepository.save(userFiles);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<UserFileDto>> getAllUserFiles() {
        List<UserFiles> all = userFilesRepository.findAll();

        return ResponseEntity.ok().body(all.stream().map(userFiles -> {
            UserFileDto userFileDto = new UserFileDto();
            userFileDto.setId(userFiles.getId());
            userFileDto.setLink(userFiles.getLink());
            userFileDto.setUser(userFiles.getUser());
            userFileDto.setType(userFiles.getType().toString());
            userFileDto.setName(userFiles.getName());
            return userFileDto;
        }).collect(Collectors.toList()));

    }

    @Override
    public UserFileDto getUserFilesById(Long id) {
        UserFiles byId = userFilesRepository.findById(id).orElse(null);

        UserFileDto userFileDto = new UserFileDto();
        userFileDto.setId(byId.getId());
        userFileDto.setUser(byId.getUser());
        userFileDto.setName(byId.getName());
        userFileDto.setLink(byId.getLink());
        userFileDto.setType(byId.getType().toString());
        return userFileDto;
    }


}
