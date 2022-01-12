package uz.pdp.hrmanagerjwtsecurityrealproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.LeaderShip;

public interface LeaderShipRepository extends JpaRepository<LeaderShip,Integer> {

    boolean existsByWorkTimeAndIdNot(String workTime, Integer id);
}
