package com.linkedin.user_service.repositories;

import com.linkedin.user_service.entities.Experience;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Long> {
    @Modifying
    @Transactional
    void deleteByIdAndUserId(Long exid, Long id);

    Optional<Experience> findByUserIdAndId(Long id, Long exId);
}
