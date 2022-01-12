package uz.pdp.hrmanagerjwtsecurityrealproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.LeaderShip;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.ApiResponse;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.LeaderShipDto;
import uz.pdp.hrmanagerjwtsecurityrealproject.service.LeaderShipService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaderShipController {
    @Autowired
    LeaderShipService leaderShipService;

    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @GetMapping("/leaderShip")
    public HttpEntity<?> getLeaderShip(){
        List<LeaderShip> leaderShip = leaderShipService.getLeaderShip();
        return ResponseEntity.ok(leaderShip);
    }
    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PostMapping("/leaderShip")
    public HttpEntity<?> addLeaderShip(@RequestBody LeaderShipDto leaderShipDto){
        ApiResponse apiResponse = leaderShipService.addLeaderShip(leaderShipDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PutMapping("/leaderShip/{id}")
    public HttpEntity<?> editLeaderShip(@PathVariable Integer id,@RequestBody LeaderShipDto leaderShipDto){
        ApiResponse apiResponse = leaderShipService.editLeaderShip(id, leaderShipDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @DeleteMapping("/tasks/{id}")
    public HttpEntity<?> deleteLeaderShip(@PathVariable Integer id){
        ApiResponse apiResponse = leaderShipService.deleteLeaderShip(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
