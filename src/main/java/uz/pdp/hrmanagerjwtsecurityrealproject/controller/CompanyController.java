package uz.pdp.hrmanagerjwtsecurityrealproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Company;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.ApiResponse;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.CompanyDto;
import uz.pdp.hrmanagerjwtsecurityrealproject.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @GetMapping("/company")
    public HttpEntity<?> getCompany(){
        List<Company> company = companyService.getCompany();
        return ResponseEntity.ok(company);
    }

    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PostMapping("/company")
    public HttpEntity<?> addCompany(@RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PutMapping("/company/{id}")
    public HttpEntity<?> editCompany(@PathVariable Integer id,@RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.editCompany(companyDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @DeleteMapping("/company/{id}")
    public HttpEntity<?> deleteCompany(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.deleteCompany(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:403).body(apiResponse);
    }
}
