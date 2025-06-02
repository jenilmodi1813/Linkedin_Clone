package com.linkedin.Post_Service.services.Impl;

import com.linkedin.Post_Service.Repositories.PostLikeRepository;
import com.linkedin.Post_Service.Repositories.PostRepository;
import com.linkedin.Post_Service.dtos.likes.CreateLikeDto;
import com.linkedin.Post_Service.dtos.likes.PostLikeDto;
import com.linkedin.Post_Service.entities.Post;
import com.linkedin.Post_Service.entities.PostLikes;
import com.linkedin.Post_Service.entities.Users.Users;
import com.linkedin.Post_Service.services.PostLikeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Override
    public ResponseEntity<String> likePost(CreateLikeDto createLikeDto, Long id) {

        Optional<Post> byId = postRepository.findById(createLikeDto.getPostId());
        Post post = byId.get();

        PostLikes prevPostLikes = postLikeRepository.findByUserIdAndPostId(id, createLikeDto.getPostId());

        if (prevPostLikes != null) {
            postLikeRepository.delete(prevPostLikes);
            return ResponseEntity.badRequest().body("Post Like removed");
        }

        Users users = new Users();
        users.setId(id);

        PostLikes postLikes = new PostLikes();
        postLikes.setPost(post);
        postLikes.setUser(users);
        postLikeRepository.save(postLikes);

        return ResponseEntity.ok().body("Post Liked");
    }

    @Override
    public ResponseEntity<List<PostLikeDto>> getAllPostLike(Long id) {
        List<PostLikes> postLikes = postLikeRepository.findAllByPostId(id);
        List<PostLikeDto> postLikeDtos = postLikes.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(postLikeDtos);
    }

    private PostLikeDto mapToDto(PostLikes postLikes) {
        PostLikeDto postLikeDto = new PostLikeDto();
        postLikeDto.setId(postLikes.getId());
        postLikeDto.setPostId(postLikes.getPost().getId());
        postLikeDto.setUserId(postLikes.getUser().getId());
        return postLikeDto;
    }
}
