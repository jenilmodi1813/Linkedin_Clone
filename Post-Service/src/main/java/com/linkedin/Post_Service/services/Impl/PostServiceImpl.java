package com.linkedin.Post_Service.services.Impl;

import com.linkedin.Post_Service.Repositories.PostFilesRepository;
import com.linkedin.Post_Service.Repositories.PostRepository;
import com.linkedin.Post_Service.config.FileUploaderHelper;
import com.linkedin.Post_Service.dtos.CreatePostDto;
import com.linkedin.Post_Service.dtos.PostDto;
import com.linkedin.Post_Service.entities.Post;
import com.linkedin.Post_Service.entities.PostFiles;
import com.linkedin.Post_Service.entities.Users.Users;
import com.linkedin.Post_Service.enums.PostFileType;
import com.linkedin.Post_Service.exception.ApiException;
import com.linkedin.Post_Service.services.PostService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostFilesRepository postFilesRepository;

    @Autowired
    private FileUploaderHelper fileUploaderHelper;

    @Override
    public ResponseEntity<PostDto> createPost(CreatePostDto createPostDto, Long id) {
        Post post = new Post();
        post.setTitle(createPostDto.getTitle());
        post.setDescription(createPostDto.getDescription());
        post.setPostedAt(new Date());

        Users user = new Users();
        user.setId(id);
        post.setPostedBy(user);
        Post save = postRepository.save(post);
        return ResponseEntity.ok().body(mapToDto(save));
    }

    @Override
    public ResponseEntity<String> uploadPostFile(MultipartFile file, String fileType, Long postId, Long id) {
        PostFileType postFileType;

        try {
            postFileType = PostFileType.valueOf(fileType.toUpperCase());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ApiException("Invalid File Type. Only " + PostFileType.IMAGE + ", " + PostFileType.VIDEO + " are supported", HttpStatus.BAD_REQUEST);
        }

        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isEmpty()) throw new ApiException("POST_DOES_NOT_EXIST", HttpStatus.NOT_FOUND);
        else if (!optionalPost.get().getPostedBy().getId().equals(id))
            throw new ApiException("You cannot upload file for someone else's post", HttpStatus.BAD_REQUEST);

        String uploadDir = "C:\\Spring_boot\\New_LinkedIn_clone\\UserPost";
        String fileName = file.getOriginalFilename();
        String fullPath = uploadDir + fileName;
        boolean b = fileUploaderHelper.uploadFile(file);
        if(!b){
            throw new ApiException("NOT_UPLOADED",HttpStatus.NOT_ACCEPTABLE);
        }
        Post post = optionalPost.get();
        PostFiles postFiles = new PostFiles();
        postFiles.setPost(post);
        postFiles.setType(postFileType);
        postFiles.setLink(fullPath);

        postFilesRepository.save(postFiles);

        return ResponseEntity.ok().body("Post Uploaded");
    }

    @Override
    public ResponseEntity<String> deletePostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) throw new ApiException("POST_DOES_NOT_EXIST",HttpStatus.NOT_FOUND);

        Post post = optionalPost.get();
        String fileUrl = null;

        Optional<PostFiles> postFiles = postFilesRepository.findByPostId(post.getId());
        if (postFiles.isPresent()) fileUrl = postFiles.get().getLink();
        postRepository.delete(post);

        return ResponseEntity.ok().body("Post Deleted ");
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setPostedAt(post.getPostedAt());
        postDto.setPostedBy(post.getPostedBy().getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());

        return postDto;
    }
}
