package com.linkedin.Post_Service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkedin.Post_Service.dtos.comment.PostCommentDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostDto {
    private Long id;
    private Long postedBy;
    private String title;
    private String description;
    private Date postedAt;
    private Integer numComments;
    private Integer numLikes;
    private List<PostCommentDto> comments;
    @JsonIgnore
    private String link;
}
