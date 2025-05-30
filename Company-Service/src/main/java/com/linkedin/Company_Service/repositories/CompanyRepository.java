package com.linkedin.Company_Service.repositories;

import com.linkedin.Company_Service.entities.company.Company;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    Optional<Company> findByName(@NotEmpty @Size(min = 3, max = 100) String name);

    @Modifying
    @Transactional
    void deleteByName(String name);
}
