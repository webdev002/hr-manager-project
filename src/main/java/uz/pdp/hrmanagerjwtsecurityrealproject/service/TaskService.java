package uz.pdp.hrmanagerjwtsecurityrealproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Employee;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Tasks;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.ApiResponse;
import uz.pdp.hrmanagerjwtsecurityrealproject.repository.TaskRepository;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.TasksDto;
import uz.pdp.hrmanagerjwtsecurityrealproject.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    JavaMailSender javaMailSender;
    public List<Tasks> getTasks() {
        List<Tasks> all = taskRepository.findAll();
        return all;
    }

    public ApiResponse addTasks(TasksDto tasksDto) {
        Tasks tasks = new Tasks();
        tasks.setName(tasksDto.getName());
        tasks.setDateline(tasksDto.getDateline());
        tasks.setTaskEmployee(employeeRepository.getById(tasksDto.getTaskEmployeeId()));
        tasks.setStatus(tasksDto.getStatus());
        tasks.setEmployeeEmail(tasksDto.getEmployeeEmail());
        taskRepository.save(tasks);

        //EMAILGA XABAR YUBORISH METHODINI CHAQIRAMIZ
        if (employeeRepository.getById(tasksDto.getTaskEmployeeId()).getEmail().equals(tasks.getEmployeeEmail())){
            sendEmail(tasksDto.getEmployeeEmail());
            return new ApiResponse("Vazifa saqlandi,Employeeni emailiga xabar jonatildi",true);
        }else {
            return null;
        }

    }

    public Boolean sendEmail(String email){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("pdp2022online@gmail.com");
            message.setTo(email);
            message.setSubject("Sizga task yuklatildi");
            message.setText("http://localhost:8081/api/task/TaskToEmployeeEmail?email="+email+"");
            javaMailSender.send(message);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ApiResponse taskEmployeeEmail(String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findAllByEmail(email);
        if (optionalEmployee.isPresent()){
            return new ApiResponse("Employee emailga xabar bordi",true);
        }
        return new ApiResponse("Employee ga allaqachon emailiga xabar borgan",false);
    }

    public ApiResponse editTask(Integer id, TasksDto tasksDto) {
        boolean existsByDatelineAndAndIdNot = taskRepository.existsByDatelineAndAndIdNot(tasksDto.getDateline(), id);
        if (existsByDatelineAndAndIdNot){
            return new ApiResponse("Bunday tasks mavjud",false);
        }
        Optional<Tasks> optionalTasks = taskRepository.findById(id);
        if (optionalTasks.isEmpty()){
            Tasks tasks = optionalTasks.get();
            tasks.setName(tasksDto.getName());
            tasks.setDateline(tasksDto.getDateline());
            tasks.setTaskEmployee(employeeRepository.getById(tasksDto.getTaskEmployeeId()));
            tasks.setStatus(tasksDto.getStatus());
            tasks.setEmployeeEmail(tasksDto.getEmployeeEmail());
            taskRepository.save(tasks);
            return new ApiResponse("Tasks tahrirlandi",true);

        }
        return new ApiResponse("Error",false);
    }

    public ApiResponse deleteTask(Integer id) {
        taskRepository.deleteById(id);
        return new ApiResponse("Tasks o'chirildi",true);
    }
}
