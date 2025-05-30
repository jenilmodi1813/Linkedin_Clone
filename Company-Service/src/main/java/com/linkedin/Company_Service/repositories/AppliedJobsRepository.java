package com.linkedin.Company_Service.repositories;

import com.linkedin.Company_Service.entities.job.AppliedJobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppliedJobsRepository extends JpaRepository<AppliedJobs,Long> {
    List<AppliedJobs> findByJobId(Long id);
}
