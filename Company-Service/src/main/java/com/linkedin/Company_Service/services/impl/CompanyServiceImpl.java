package com.linkedin.Company_Service.services.impl;

import com.linkedin.Company_Service.config.UserServiceClient;
import com.linkedin.Company_Service.dtos.company.CompanyDto;
import com.linkedin.Company_Service.dtos.company.CreateCompanyDto;
import com.linkedin.Company_Service.dtos.company.UpdateCompanyDto;
import com.linkedin.Company_Service.dtos.company.location.CompanyLocationDto;
import com.linkedin.Company_Service.dtos.company.location.CreateCompanyLocationDto;
import com.linkedin.Company_Service.dtos.company.location.UpdateCompanyLocationDto;
import com.linkedin.Company_Service.dtos.users.UserDto;
import com.linkedin.Company_Service.dtos.users.UserProfileDto;
import com.linkedin.Company_Service.entities.Users.Users;
import com.linkedin.Company_Service.entities.company.Company;
import com.linkedin.Company_Service.entities.company.CompanyLocations;
import com.linkedin.Company_Service.exception.ApiException;
import com.linkedin.Company_Service.repositories.CompanyLocationRepository;
import com.linkedin.Company_Service.repositories.CompanyRepository;
import com.linkedin.Company_Service.repositories.UserRepository;
import com.linkedin.Company_Service.services.CompanyService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final String LOGIN_PLEASE = "User not Login";
    private final String USER_DOES_NOT_EXIST = "user does not exist";
    private final String COMPANY_WITH_THESE_NAME = "Company With Name Already Exist";
    private final String COMPANY_NOT_EXIST = "Company Not Exist";
    private final String COMPANY_LOCATION_NOT_EXIST = "Company Location Not Exist";
//    private final String USER_ID_NOT_EXIST = "user id not exist";

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private CompanyLocationRepository companyLocationRepository;

    @Override
    public ResponseEntity<Void> createCompany(CreateCompanyDto createCompanyDto, Long id) {
        UserDto userDto;
        try {
            userDto = userServiceClient.getUserById(id);
            if (userDto == null) {
                throw new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND);
            }
        } catch (FeignException.BadRequest request) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }

        Users byEmail = userRepository.findByEmail(userDto.getEmail()).orElse(null);

        Optional<Company> byName = companyRepository.findByName(createCompanyDto.getName());
        if (byName.isPresent()) {
            throw new ApiException(COMPANY_WITH_THESE_NAME, HttpStatus.FOUND);
        }

        Company company = new Company();
        company.setCreatedBy(byEmail);
        company.setName(createCompanyDto.getName());
        company.setAbout(createCompanyDto.getAbout());
        company.setNumEmployees(createCompanyDto.getNumEmployees());
        company.setWebsite(createCompanyDto.getWebsite());
        company.setHeadLine(createCompanyDto.getHeadLine());

        companyRepository.save(company);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CompanyDto> getCompanyByName(String name, Long id) {

        UserDto userDto;
        try {
            userDto = userServiceClient.getUserById(id);
            if (userDto == null) {
                throw new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND);
            }
        } catch (FeignException.BadRequest request) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }

        Company byName = companyRepository.findByName(name).orElseThrow(() -> new ApiException(COMPANY_NOT_EXIST, HttpStatus.NOT_FOUND));

        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(byName.getId());
        companyDto.setCreatedBy(byName.getCreatedBy().getId());
        companyDto.setName(byName.getName());
        companyDto.setWebsite(byName.getWebsite());
        companyDto.setAbout(byName.getAbout());
        companyDto.setHeadLine(byName.getHeadLine());
        companyDto.setNumEmployees(byName.getNumEmployees());

        return ResponseEntity.ok().body(companyDto);
    }

    @Override
    public ResponseEntity<Void> updateCompanyByName(String name, UpdateCompanyDto updateCompanyDto) {

        Company company = companyRepository.findByName(name).orElseThrow(() -> new ApiException(COMPANY_NOT_EXIST, HttpStatus.NOT_FOUND));

        company.setName(updateCompanyDto.getName());
        company.setAbout(updateCompanyDto.getAbout());
        company.setNumEmployees(updateCompanyDto.getNumEmployees());
        company.setWebsite(updateCompanyDto.getWebsite());
        company.setHeadLine(updateCompanyDto.getHeadLine());

        companyRepository.save(company);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteCompanyByName(String name) {
        Company company = companyRepository.findByName(name).orElseThrow(() -> new ApiException(COMPANY_NOT_EXIST, HttpStatus.NOT_FOUND));
        companyRepository.deleteByName(name);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> addCompanyLocation(CreateCompanyLocationDto createCompanyLocationsDto, String name) {
        Company company = companyRepository.findByName(name).orElseThrow(() -> new ApiException(COMPANY_NOT_EXIST, HttpStatus.NOT_FOUND));

        Set<CompanyLocations> companyLocationsList = new HashSet<>();

        CompanyLocations companyLocations = new CompanyLocations();
        companyLocations.setCompany(company);
        companyLocations.setAddress(createCompanyLocationsDto.getAddress());
        companyLocations.setZipCode(createCompanyLocationsDto.getZipCode());
        companyLocations.setCountry(createCompanyLocationsDto.getCountry());
        companyLocations.setCity(createCompanyLocationsDto.getCity());
        companyLocationsList.add(companyLocations);
        company.setLocations(companyLocationsList);
        companyLocationRepository.save(companyLocations);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateCompanyLocation(UpdateCompanyLocationDto updateCompanyLocationDto, Long id) {
        CompanyLocations byId = companyLocationRepository.findById(id).orElseThrow(() -> new ApiException(COMPANY_LOCATION_NOT_EXIST, HttpStatus.NOT_FOUND));
        byId.setCountry(updateCompanyLocationDto.getCountry());
        byId.setCity(updateCompanyLocationDto.getCity());
        byId.setAddress(updateCompanyLocationDto.getAddress());
        byId.setZipCode(updateCompanyLocationDto.getZipCode());
        companyLocationRepository.save(byId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteCompanyLocation(Long id) {
        CompanyLocations byId = companyLocationRepository.findById(id).orElseThrow(() -> new ApiException(COMPANY_LOCATION_NOT_EXIST, HttpStatus.NOT_FOUND));
        companyLocationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Set<CompanyLocationDto>> getAllLocatonByCompany(String name) {

        Company byName = companyRepository.findByName(name).orElseThrow(() -> new ApiException(COMPANY_NOT_EXIST, HttpStatus.NOT_FOUND));
        Set<CompanyLocations> locations = byName.getLocations();

        Set<CompanyLocationDto> locationDtos = locations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(locationDtos);
    }

    private CompanyLocationDto convertToDto(CompanyLocations location) {
        CompanyLocationDto dto = new CompanyLocationDto();
        dto.setId(location.getId());
        dto.setZipCode(location.getZipCode());
        dto.setCity(location.getCity());
        dto.setCountry(location.getCountry());
        dto.setAddress(location.getAddress());
        return dto;
    }
}
