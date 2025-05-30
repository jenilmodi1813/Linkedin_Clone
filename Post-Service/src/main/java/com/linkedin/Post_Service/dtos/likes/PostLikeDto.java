package com.linkedin.Post_Service.dtos.likes;

import lombok.Data;

@Data
public class PostLikeDto {
    private Long id;
    private Long userId;
    private Long postId;
}
