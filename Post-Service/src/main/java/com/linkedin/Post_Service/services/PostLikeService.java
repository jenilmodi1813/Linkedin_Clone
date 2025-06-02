package com.linkedin.Post_Service.services;

import com.linkedin.Post_Service.dtos.likes.CreateLikeDto;
import com.linkedin.Post_Service.dtos.likes.PostLikeDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostLikeService {

    ResponseEntity<String> likePost(@Valid CreateLikeDto createLikeDto, Long id);

    ResponseEntity<List<PostLikeDto>> getAllPostLike(Long id);
}
