package com.linkedin.Post_Service.services;

import com.linkedin.Post_Service.dtos.comment.CreatePostCommentDto;
import com.linkedin.Post_Service.dtos.comment.PostCommentDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostCommentService {
    ResponseEntity<PostCommentDto> addPostComment(@Valid CreatePostCommentDto createPostCommentDto, Long userId);

    ResponseEntity<PostCommentDto> getCommentById(Long id);

    ResponseEntity<List<PostCommentDto>> getAllCommentByPost(Long id);

    ResponseEntity<String> deleteCommentById(Long id);
}
