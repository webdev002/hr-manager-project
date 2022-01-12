package uz.pdp.hrmanagerjwtsecurityrealproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
 boolean existsByNameAndIdNot(String name, Integer id);
}