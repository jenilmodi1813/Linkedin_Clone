package com.linkedin.Post_Service.dtos.comment;

import lombok.Data;

import java.util.Date;

@Data
public class PostCommentDto {
    private Long id;
    private Long post;
    private Long user;
    private Long parent;
    private String text;
    private Date postedAt;
}
