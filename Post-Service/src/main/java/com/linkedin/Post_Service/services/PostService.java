package com.linkedin.Post_Service.services;

import com.linkedin.Post_Service.dtos.CreatePostDto;
import com.linkedin.Post_Service.dtos.PostDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    ResponseEntity<PostDto> createPost(@Valid CreatePostDto createPostDto, Long id);

    ResponseEntity<String> uploadPostFile(MultipartFile file, String fileType, Long postId, Long id);

    ResponseEntity<String> deletePostById(Long id);
}
