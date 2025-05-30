package com.linkedin.user_service.repositories;

import com.linkedin.user_service.entities.UserFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFilesRepository extends JpaRepository<UserFiles,Long> {
}
