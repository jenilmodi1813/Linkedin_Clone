package com.linkedin.Post_Service.controller;

import com.linkedin.Post_Service.dtos.comment.CreatePostCommentDto;
import com.linkedin.Post_Service.dtos.comment.PostCommentDto;
import com.linkedin.Post_Service.services.PostCommentService;
import com.netflix.discovery.converters.Auto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class PostCommentController {

    @Autowired
    private PostCommentService postCommentService;

    @PostMapping("/{userId}")
    public ResponseEntity<PostCommentDto> addCommentToPost(@Valid @RequestBody CreatePostCommentDto createPostCommentDto,@PathVariable  Long userId) {

        return postCommentService.addPostComment(createPostCommentDto, userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostCommentDto> getCommentById(@PathVariable("id") Long id) {
        return postCommentService.getCommentById(id);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<PostCommentDto>> getAllCommentByPost(@PathVariable("id") Long id) {
        return postCommentService.getAllCommentByPost(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("id") Long id) {
        return postCommentService.deleteCommentById(id);
    }

}
