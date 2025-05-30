package com.linkedin.Company_Service.controller;

import com.linkedin.Company_Service.dtos.company.CompanyDto;
import com.linkedin.Company_Service.dtos.company.CreateCompanyDto;
import com.linkedin.Company_Service.dtos.company.UpdateCompanyDto;
import com.linkedin.Company_Service.dtos.company.location.CompanyLocationDto;
import com.linkedin.Company_Service.dtos.company.location.CreateCompanyLocationDto;
import com.linkedin.Company_Service.dtos.company.location.UpdateCompanyLocationDto;
import com.linkedin.Company_Service.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/createCompany")
    public ResponseEntity<Void> createCompany(@Valid @RequestBody CreateCompanyDto createCompanyDto,@RequestParam Long id){
        return companyService.createCompany(createCompanyDto,id);
    }

    @GetMapping("getCompanyByName/{name}")
    public ResponseEntity<CompanyDto> getCompanyByName(@PathVariable String name,@RequestParam Long id){
        return  companyService.getCompanyByName(name,id);
    }

    @PutMapping("UpdateCompanyByName/{name}")
    public ResponseEntity<Void> UpdateCompanyByName(@PathVariable String name, @RequestBody UpdateCompanyDto updateCompanyDto){
        return companyService.updateCompanyByName(name,updateCompanyDto);
    }

    @DeleteMapping("deleteCompanyByName/{name}")
    public ResponseEntity<Void> deleteCompanyByName(@PathVariable String name){
        return companyService.deleteCompanyByName(name);
    }

    @PostMapping("/addCompanyLocation")
    public ResponseEntity<Void> addCompanyLocation(@Valid @RequestBody CreateCompanyLocationDto createCompanyLocationsDto, @RequestParam String name){
        return companyService.addCompanyLocation(createCompanyLocationsDto,name);
    }

    @PutMapping("updateCompanyLocation/{id}")
    public ResponseEntity<Void> updateCompanyLocation(@Valid @RequestBody UpdateCompanyLocationDto updateCompanyLocationDto, @PathVariable Long id) {
        return companyService.updateCompanyLocation(updateCompanyLocationDto, id);
    }

    @DeleteMapping("deleteCompanyLocation/{id}")
    public ResponseEntity<Void> deleteCompanyLocation(@PathVariable Long id) {
        return companyService.deleteCompanyLocation(id);
    }

    @GetMapping("getAllLocatonByCompany/{name}")
    public  ResponseEntity<Set<CompanyLocationDto>> getAllLocatonByCompany(@PathVariable String name){
        return companyService.getAllLocatonByCompany(name);
    }

}
