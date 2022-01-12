package uz.pdp.hrmanagerjwtsecurityrealproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> findByEmailAndEmailCode(String email, String emailCode);

    Optional<User> findByEmail(String username);
}
