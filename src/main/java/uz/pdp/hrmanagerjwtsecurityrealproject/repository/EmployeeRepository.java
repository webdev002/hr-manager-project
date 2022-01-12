package uz.pdp.hrmanagerjwtsecurityrealproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Employee;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByEmail(@Email String email);

    Optional<Employee> findAllByEmail(@Email String email);


    boolean existsByEmailAndIdNot(@Email String email, UUID id);
}
