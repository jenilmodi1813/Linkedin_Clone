package com.linkedin.Post_Service.Repositories;

import com.linkedin.Post_Service.entities.PostLikes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikes,Long> {


    PostLikes findByUserIdAndPostId(Long id, @NotNull @Positive long postId);

    List<PostLikes> findAllByPostId(Long id);
}
