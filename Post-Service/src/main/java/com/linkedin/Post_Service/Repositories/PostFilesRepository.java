package com.linkedin.Post_Service.Repositories;

import com.linkedin.Post_Service.entities.PostFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostFilesRepository extends JpaRepository<PostFiles,Long> {
    Optional<PostFiles> findByPostId(Long id);
}
