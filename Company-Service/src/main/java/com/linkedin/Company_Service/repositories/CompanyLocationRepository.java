package com.linkedin.Company_Service.repositories;

import com.linkedin.Company_Service.entities.company.CompanyLocations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyLocationRepository extends JpaRepository<CompanyLocations,Long> {
}
