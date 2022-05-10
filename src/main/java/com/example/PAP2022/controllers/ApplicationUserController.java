package com.example.PAP2022.controllers;

import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.Image;
import com.example.PAP2022.payload.ApplicationUserRequest;
import com.example.PAP2022.services.ApplicationUserDetailsService;
import com.example.PAP2022.services.ImageService;
import com.example.PAP2022.services.TaskService;
import com.example.PAP2022.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class ApplicationUserController {

    private final TeamService teamService;
    private final ApplicationUserDetailsService applicationUserService;
    private final TaskService taskService;
    private final ImageService imageService;
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

    @GetMapping("/image")
    public ResponseEntity<?> getImage(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            Image image = applicationUserService.getImage(id);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(image.getType()))
                    .body(ImageService.decompressImage(image.getImage()));
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

    // TODO do refaktoryzacji !!!!!!
    @PutMapping("/edit")
    public ResponseEntity<?> editUser(@RequestParam Long id,
                                      @RequestParam("image") MultipartFile file,
                                      @RequestBody ApplicationUserRequest applicationUserRequest) throws IOException {
        Image image;
        try {
            image = imageService.convertToImage(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            ApplicationUser applicationUser = applicationUserService
                    .loadApplicationUserById(id).get();

            applicationUser.setFirstName(applicationUserRequest.getFirstName());
            applicationUser.setLastName(applicationUserRequest.getLastName());
            applicationUser.setImage(image);
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
