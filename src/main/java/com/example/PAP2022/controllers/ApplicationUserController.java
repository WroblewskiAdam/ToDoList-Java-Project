package com.example.PAP2022.controllers;

import com.example.PAP2022.enums.ApplicationUserRole;
import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.payload.ApplicationUserRequest;
import com.example.PAP2022.services.ApplicationUserDetailsServiceImplementation;
import com.example.PAP2022.services.TaskService;
import com.example.PAP2022.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class ApplicationUserController {

    private final TeamService teamService;
    private final ApplicationUserDetailsServiceImplementation applicationUserService;
    private final TaskService taskService;
    private final PasswordEncoder encoder;

    @GetMapping("/get")
    public ResponseEntity<?> getApplicationUser(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(applicationUserService.loadApplicationUserById(id).get());
        } else {
            return ResponseEntity.badRequest().body(new UserNotFoundException("Could not find student with ID " + id).getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(applicationUserService.getAllUsers());
    }

    @GetMapping("/teams")
    public ResponseEntity<?> getAllTeams(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(applicationUserService.getAllTeams(id));
        } else {
            return ResponseEntity.badRequest().body(new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(applicationUserService.getAllTasks(id));
        } else {
            return ResponseEntity.badRequest().body(new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok(applicationUserService.deleteUser(id));
        } else {
            return ResponseEntity.badRequest().body(new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editUser(@RequestParam Long id, @RequestBody ApplicationUserRequest applicationUserRequest) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            ApplicationUser applicationUser = applicationUserService
                    .loadApplicationUserById(id).get();

            applicationUser.setFirstName(applicationUserRequest.getFirstName());
            applicationUser.setLastName(applicationUserRequest.getLastName());
            applicationUser.setImg(applicationUserRequest.getImg());
            applicationUser.setPassword(encoder.encode(applicationUserRequest.getPassword()));

            return ResponseEntity.ok(applicationUserService.editApplicationUser(applicationUser));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id)
                            .getMessage()
            );
        }
    }

}
