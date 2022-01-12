package uz.pdp.hrmanagerjwtsecurityrealproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Role;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleName(RoleName rolename);
}
