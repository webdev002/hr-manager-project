package uz.pdp.hrmanagerjwtsecurityrealproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Employee;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.ApiResponse;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.EmployeeDto;
import uz.pdp.hrmanagerjwtsecurityrealproject.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    JavaMailSender javaMailSender;
//    @Autowired
//    AuthenticationManager authenticationManager;
//    @Autowired
//    @Lazy
//    PasswordEncoder passwordEncoder;

    public List<Employee> getEmployee() {
        List<Employee> all = employeeRepository.findAll();
        return all;
    }

    public ApiResponse addEmployee(EmployeeDto employeeDto) {
        boolean existsByEmail = employeeRepository.existsByEmail(employeeDto.getEmail());
        if (existsByEmail){
            return new ApiResponse("Bunday email mavjud",false);
        }

        Employee employee = new Employee();
        employee.setFirstname(employeeDto.getFirstname());
        employee.setLastname(employeeDto.getLastname());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(employeeDto.getPassword());
        employeeRepository.save(employee);
        //EMAILGA XABAR YUBORISH METHODINI CHAQIRAMIZ
        sendEmail(employee.getEmail());
        return new ApiResponse("Muvaffaqiyatli Companyga qoshildingiz,Email Akkountingizdagi link orqali o'zingiz uchun kod qoying",true);


    }

    public Boolean sendEmail(String email){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("TestPdpOnline@gmail.com");
            message.setTo(email);
            message.setSubject("Akkountni tasdiqlash");
            message.setText("http://localhost:8081/api/employee/employeeEmail?email="+email+"");
            javaMailSender.send(message);
            return true;

        }catch (Exception e){
            return false;
        }
    }

    public ApiResponse employeeEmail(String email){
        Optional<Employee> optionalEmployee = employeeRepository.findAllByEmail(email);
        if (optionalEmployee.isPresent()){
            return new ApiResponse("Employee emailga xabar bordi",true);
        }
        return new ApiResponse("Employee ga allaqachon emailiga xabar borgan",false);
    }

    public ApiResponse editEmployee(UUID id, EmployeeDto employeeDto) {
        boolean existsByEmailAndIdNot = employeeRepository.existsByEmailAndIdNot(employeeDto.getEmail(), id);
        if (existsByEmailAndIdNot){
            return new ApiResponse("Bunday employee mavjud",false);
        }
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (!optionalEmployee.isEmpty()){
            Employee employee = optionalEmployee.get();
            employee.setFirstname(employeeDto.getFirstname());
            employee.setLastname(employeeDto.getLastname());
            employee.setEmail(employeeDto.getEmail());
            employee.setPassword(employeeDto.getPassword());
            employeeRepository.save(employee);
            return new ApiResponse("Employee tahrirlandi",true);
        }
        return new ApiResponse("Error",false);
    }

    public ApiResponse deleteEmployee(UUID id) {
        employeeRepository.deleteById(id);
        return new ApiResponse("Employee ochirildi",true);
    }
}
