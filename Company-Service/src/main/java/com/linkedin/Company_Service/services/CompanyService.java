package com.linkedin.Company_Service.services;

import com.linkedin.Company_Service.dtos.company.CompanyDto;
import com.linkedin.Company_Service.dtos.company.CreateCompanyDto;
import com.linkedin.Company_Service.dtos.company.UpdateCompanyDto;
import com.linkedin.Company_Service.dtos.company.location.CompanyLocationDto;
import com.linkedin.Company_Service.dtos.company.location.CreateCompanyLocationDto;
import com.linkedin.Company_Service.dtos.company.location.UpdateCompanyLocationDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface CompanyService {
    ResponseEntity<Void> createCompany(@Valid CreateCompanyDto createCompanyDto, Long id);

    ResponseEntity<CompanyDto> getCompanyByName(String name,Long id);

    ResponseEntity<Void> updateCompanyByName(String name, UpdateCompanyDto updateCompanyDto);

    ResponseEntity<Void> deleteCompanyByName(String name);

    ResponseEntity<Void> addCompanyLocation(@Valid CreateCompanyLocationDto createCompanyLocationsDto, String name);

    ResponseEntity<Void> updateCompanyLocation(@Valid UpdateCompanyLocationDto updateCompanyLocationDto, Long id);

    ResponseEntity<Void> deleteCompanyLocation(Long id);

    ResponseEntity<Set<CompanyLocationDto>> getAllLocatonByCompany(String name);
}
