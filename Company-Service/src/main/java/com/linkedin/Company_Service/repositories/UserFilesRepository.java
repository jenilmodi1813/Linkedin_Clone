package com.linkedin.Company_Service.repositories;


import com.linkedin.Company_Service.entities.Users.UserFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFilesRepository extends JpaRepository<UserFiles,Long> {
}
