package com.linkedin.user_service.controller;

import com.linkedin.user_service.dtos.resume.UserFileDto;
import com.linkedin.user_service.dtos.resume.UserResumeDto;
import com.linkedin.user_service.services.UserFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("users/files")
public class FileController {

    @Autowired
    private UserFilesService userFilesService;

    @PostMapping("")
    public ResponseEntity<Void> uploadFIle(@RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType,@RequestParam Long id){
        return userFilesService.uploadFIle(file,fileType,id);
    }

    @GetMapping("")
    public ResponseEntity<List<UserFileDto>> getAllUserFiles(){
        return userFilesService.getAllUserFiles();
    }

    @GetMapping("/{id}")
    public UserFileDto getUserFilesById(@PathVariable Long id ){
        return userFilesService.getUserFilesById(id);
    }



}
