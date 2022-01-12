package uz.pdp.hrmanagerjwtsecurityrealproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Employee;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.ApiResponse;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.EmployeeDto;
import uz.pdp.hrmanagerjwtsecurityrealproject.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @GetMapping("/employee")
    public HttpEntity<?> getEmployee(){
        List<Employee> employee = employeeService.getEmployee();
        return ResponseEntity.ok(employee);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @PostMapping("/employee")
    public HttpEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto){
        ApiResponse apiResponse = employeeService.addEmployee(employeeDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('EMPLOYEE')")
    @GetMapping("/employeeEmailCode")
    public HttpEntity<?> employeeEmail(@RequestParam(required = false) String email){
        ApiResponse apiResponse = employeeService.employeeEmail(email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('EMPLOYEE')")
    @PutMapping("/employee/{id}")
    public HttpEntity<?> editEmployee(@PathVariable UUID id,@RequestBody EmployeeDto employeeDto){
        ApiResponse apiResponse = employeeService.editEmployee(id, employeeDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @DeleteMapping("/employee/{id}")
    public HttpEntity<?> deleteEmployee(@PathVariable UUID id){
        ApiResponse apiResponse = employeeService.deleteEmployee(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

}
