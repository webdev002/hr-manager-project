package uz.pdp.hrmanagerjwtsecurityrealproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Company;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.ApiResponse;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.CompanyDto;
import uz.pdp.hrmanagerjwtsecurityrealproject.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public List<Company> getCompany() {
        List<Company> companyList = companyRepository.findAll();
        return companyList;
    }

    public ApiResponse addCompany(CompanyDto companyDto) {
        Company company =new Company();
        company.setName(companyDto.getName());
        company.setDirector(companyDto.getDirector());
        companyRepository.save(company);
        return new ApiResponse("Company Saqlandi",true);
    }

    public ApiResponse editCompany(CompanyDto companyDto,Integer id) {
        boolean existsByNameAndIdNot = companyRepository.existsByNameAndIdNot(companyDto.getName(), id);
        if (existsByNameAndIdNot){
            return new ApiResponse("Bunday company mavjud emas",false);
        }
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isEmpty()){
            Company company = optionalCompany.get();
            company.setName(companyDto.getName());
            company.setDirector(companyDto.getDirector());
            companyRepository.save(company);
            return new ApiResponse("Company Tahrirlandi",true);

        }
        return new ApiResponse("Error",false);

    }

    public ApiResponse deleteCompany(Integer id) {
        companyRepository.deleteById(id);
        return new ApiResponse("Company Ochirildi",true);
    }

}
