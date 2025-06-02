package com.linkedin.Post_Service.Repositories;

import com.linkedin.Post_Service.entities.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment,Long> {
    List<PostComment> findAllByPostId(Long id);
}
