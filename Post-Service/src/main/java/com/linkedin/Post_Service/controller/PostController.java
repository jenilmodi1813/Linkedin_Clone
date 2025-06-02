package com.linkedin.Post_Service.controller;

import com.linkedin.Post_Service.dtos.CreatePostDto;
import com.linkedin.Post_Service.dtos.PostDto;
import com.linkedin.Post_Service.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostDto createPostDto, @RequestParam Long id) {

        return postService.createPost(createPostDto, id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPostFile(@RequestParam("file") MultipartFile file, @RequestParam("type") String fileType,
                                                 @RequestParam("postId") Long postId,@RequestParam("userId") Long id) {
        return postService.uploadPostFile(file, fileType, postId,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") Long id) {
        return postService.deletePostById(id);
    }

    @GetMapping("")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {

        return postService.getPostById(id);
    }
}
