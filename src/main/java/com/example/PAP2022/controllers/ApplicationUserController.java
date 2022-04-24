package com.example.PAP2022.controllers;

import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.Task;
import com.example.PAP2022.models.Team;
import com.example.PAP2022.services.ApplicationUserDetailsServiceImplementation;
import com.example.PAP2022.services.TaskService;
import com.example.PAP2022.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class ApplicationUserController {
    private final TeamService teamService;
    private final ApplicationUserDetailsServiceImplementation applicationUserService;
    private final TaskService taskService;

    @GetMapping("/get")
    public ResponseEntity<ApplicationUser> getApplicationUser(@RequestParam Long id){
        return ResponseEntity.ok().body(applicationUserService.getApplicationUserById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationUser>>getUsers() {
        return ResponseEntity.ok().body(applicationUserService.getAllUsers());
    }

    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams(@RequestParam Long id){
        return ResponseEntity.ok().body(applicationUserService.getAllTeams(id));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam Long id){
        return ResponseEntity.ok().body(applicationUserService.getAllTasks(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestParam Long id){
        return ResponseEntity.ok(applicationUserService.deleteUser(id));
    }
}
