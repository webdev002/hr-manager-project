package uz.pdp.hrmanagerjwtsecurityrealproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Tasks;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.ApiResponse;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.EmployeeDto;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.TasksDto;
import uz.pdp.hrmanagerjwtsecurityrealproject.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @GetMapping("/tasks")
    public HttpEntity<?> getTasks(){
        List<Tasks> tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @PostMapping("/tasks")
    public HttpEntity<?> addTasks(@RequestBody TasksDto tasksDto){
        ApiResponse apiResponse = taskService.addTasks(tasksDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('EMPLOYEE')")
    @GetMapping("/task/employeeEmail")
    public HttpEntity<?> taskEmployeeEmail(@RequestParam(required = false) String email){
        ApiResponse apiResponse = taskService.taskEmployeeEmail(email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @PutMapping("/task/{id}")
    public HttpEntity<?> editTask(@PathVariable Integer id,@RequestBody TasksDto tasksDto){
        ApiResponse apiResponse = taskService.editTask(id, tasksDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:403).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @DeleteMapping("/task/{id}")
    public HttpEntity<?> deleteTask(@PathVariable Integer id){
        ApiResponse apiResponse = taskService.deleteTask(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



}
