package uz.pdp.hrmanagerjwtsecurityrealproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Tasks;

@EnableJpaRepositories
public interface TaskRepository extends JpaRepository<Tasks,Integer> {
    boolean existsByDatelineAndAndIdNot(String dateLine,Integer id);
}
