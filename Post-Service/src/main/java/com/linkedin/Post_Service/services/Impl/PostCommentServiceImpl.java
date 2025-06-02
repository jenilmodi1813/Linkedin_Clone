package com.linkedin.Post_Service.services.Impl;

import com.linkedin.Post_Service.Repositories.PostCommentRepository;
import com.linkedin.Post_Service.Repositories.PostRepository;
import com.linkedin.Post_Service.dtos.comment.CreatePostCommentDto;
import com.linkedin.Post_Service.dtos.comment.PostCommentDto;
import com.linkedin.Post_Service.entities.Post;
import com.linkedin.Post_Service.entities.PostComment;
import com.linkedin.Post_Service.entities.Users.Users;
import com.linkedin.Post_Service.exception.ApiException;
import com.linkedin.Post_Service.services.PostCommentService;
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
public class PostCommentServiceImpl implements PostCommentService {
    private final String PARENT_COMMENT_DOES_NOT_EXIST = "parent comment does not exist";

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;
    

    @Override
    public ResponseEntity<PostCommentDto> addPostComment(CreatePostCommentDto createPostCommentDto, Long userId) {

        Optional<Post> byId = postRepository.findById(createPostCommentDto.getPostId());
        Post post = byId.get();

        Users users = new Users();
        users.setId(userId);

        PostComment postComment = new PostComment();
        postComment.setPostedAt(new Date());
        postComment.setPost(post);
        postComment.setUser(users);
        postComment.setText(createPostCommentDto.getText());

        Long parentId = createPostCommentDto.getParentId();
        if (parentId != null) {
            Optional<PostComment> optionalPostComment = postCommentRepository.findById(parentId);

            if (optionalPostComment.isEmpty()) throw new ApiException(PARENT_COMMENT_DOES_NOT_EXIST,HttpStatus.NOT_FOUND);

            PostComment parentComment = optionalPostComment.get();

            postComment.setParent(parentComment);
        }
        PostComment save = postCommentRepository.save(postComment);

        return ResponseEntity.ok().body(mapToDto(postComment));
    }

    @Override
    public ResponseEntity<PostCommentDto> getCommentById(Long id) {
        Optional<PostComment> byId = postCommentRepository.findById(id);
        PostComment postComment = byId.get();

        return ResponseEntity.ok().body(mapToDto(postComment));
    }

    @Override
    public ResponseEntity<List<PostCommentDto>> getAllCommentByPost(Long id) {
        List<PostComment> allByPostId = postCommentRepository.findAllByPostId(id);
        List<PostCommentDto> postCommentDtos = allByPostId.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(postCommentDtos);
    }

    @Override
    public ResponseEntity<String> deleteCommentById(Long id) {
        postCommentRepository.deleteById(id);
        return ResponseEntity.ok().body("Post Comment deleted successfully");
    }


    private PostCommentDto mapToDto(PostComment comment) {
        PostCommentDto dto = new PostCommentDto();
        dto.setId(comment.getId());
        dto.setUser(comment.getUser().getId());
        dto.setText(comment.getText());
        if(comment.getParent() != null) dto.setParent(comment.getParent().getId());
        dto.setPost(comment.getPost().getId());
        dto.setPostedAt(comment.getPostedAt());

        return dto;
    }
}
