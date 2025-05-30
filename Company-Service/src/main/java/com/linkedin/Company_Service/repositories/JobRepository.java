package com.linkedin.Company_Service.repositories;

import com.linkedin.Company_Service.entities.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    List<Job> findByCategoryId(Long id);

    List<Job> findByCompanyId(Long id);
}
