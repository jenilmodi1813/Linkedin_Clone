package com.linkedin.Post_Service.controller;

import com.linkedin.Post_Service.dtos.likes.CreateLikeDto;
import com.linkedin.Post_Service.dtos.likes.PostLikeDto;
import com.linkedin.Post_Service.services.PostLikeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class PostaLikeController {

    @Autowired
    private PostLikeService postLikeService;

    @PostMapping("/{id}")
    public ResponseEntity<String> likePost(@Valid @RequestBody CreateLikeDto createLikeDto,@PathVariable Long id) {
        return postLikeService.likePost(createLikeDto, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PostLikeDto>> getAllPostLike(@PathVariable Long id){
        return  postLikeService.getAllPostLike(id);
    }
}
