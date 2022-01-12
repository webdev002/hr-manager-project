package uz.pdp.hrmanagerjwtsecurityrealproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Employee;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.LeaderShip;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Tasks;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.ApiResponse;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.LeaderShipDto;
import uz.pdp.hrmanagerjwtsecurityrealproject.repository.EmployeeRepository;
import uz.pdp.hrmanagerjwtsecurityrealproject.repository.LeaderShipRepository;
import uz.pdp.hrmanagerjwtsecurityrealproject.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LeaderShipService {
    @Autowired
    LeaderShipRepository leaderShipRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    JavaMailSender javaMailSender;
    public List<LeaderShip> getLeaderShip() {
        List<LeaderShip> leaderShips = leaderShipRepository.findAll();
        return leaderShips;
    }

    public ApiResponse addLeaderShip(LeaderShipDto leaderShipDto) {
        LeaderShip leaderShip = new LeaderShip();
        leaderShip.setEmployeeSalary(leaderShipDto.getEmployeeSalary());
        leaderShip.setEmployee((Set<Employee>) employeeRepository.getById(leaderShipDto.getEmployee()));
        leaderShip.setTasks((Set<Tasks>) taskRepository.getById(leaderShipDto.getTasks()));
        leaderShip.setWorkTime(leaderShipDto.getWorkTime());
        leaderShipRepository.save(leaderShip);
        return new ApiResponse("Leadership saqlandi",true);
    }

    public ApiResponse editLeaderShip(Integer id, LeaderShipDto leaderShipDto) {
        boolean existsByWorkTimeAndIdNot = leaderShipRepository.existsByWorkTimeAndIdNot(leaderShipDto.getWorkTime(), id);
        if (existsByWorkTimeAndIdNot){
            return new ApiResponse("Bunday leaderShip mavjud emas",false);
        }
        Optional<LeaderShip> optionalLeaderShip = leaderShipRepository.findById(id);
        if (!optionalLeaderShip.isPresent()){
            LeaderShip leaderShip = optionalLeaderShip.get();
            leaderShip.setEmployeeSalary(leaderShipDto.getEmployeeSalary());
            leaderShip.setEmployee((Set<Employee>) employeeRepository.getById(leaderShipDto.getEmployee()));
            leaderShip.setTasks((Set<Tasks>) taskRepository.getById(leaderShipDto.getTasks()));
            leaderShip.setWorkTime(leaderShipDto.getWorkTime());
            leaderShipRepository.save(leaderShip);
            return new ApiResponse("Leadership tahrirlandi",true);
        }
        return new ApiResponse("Error",false);
    }

    public ApiResponse deleteLeaderShip(Integer id) {
        leaderShipRepository.deleteById(id);
        return new ApiResponse("LeaderShip Ochirildi",true);
    }
}
