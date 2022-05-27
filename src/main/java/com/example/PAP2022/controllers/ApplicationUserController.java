package com.example.PAP2022.controllers;

import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.models.Image;
import com.example.PAP2022.payload.AppUserEditRequest;
import com.example.PAP2022.services.ApplicationUserDetailsService;
import com.example.PAP2022.services.ImageService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class ApplicationUserController {

    private final ApplicationUserDetailsService applicationUserService;

    @GetMapping("/get")
    public ResponseEntity<?> getApplicationUser(@RequestParam Long id) {
        try{
            return ResponseEntity.ok().body(applicationUserService.getApplicationUser(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get_by_email")
    public ResponseEntity<?> getApplicationUserByEmail(@RequestParam String email) {
        try{
            return ResponseEntity.ok().body(applicationUserService.getApplicationUserByEmail(email));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(applicationUserService.getAllUsers());
    }

    @GetMapping("/teams")
    public ResponseEntity<?> getAllTeams(@RequestParam Long id) {
        try{
            return ResponseEntity.ok().body(applicationUserService.getAllTeams(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(@RequestParam Long id) {
        try{
            return ResponseEntity.ok().body(applicationUserService.getAllTasks(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/image")
    public ResponseEntity<?> getImage(@RequestParam Long id) {
        try {
            Image image = applicationUserService.getImage(id);
            System.out.println(image.getImage());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(image.getType()))
                    .body(ImageService.decompressImage(image.getImage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editUser(@RequestParam("id") Long id, @RequestBody AppUserEditRequest request) {

        try{
            return ResponseEntity.ok(applicationUserService.editApplicationUser(
                    id,
                    request
                    ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/change_role")
    public ResponseEntity<?> changeUserRole(@RequestParam("id") Long id, @RequestParam("role") String role){
        try{
            return ResponseEntity.ok(applicationUserService.changeRole(id, role));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long id){
        try{
            return ResponseEntity.ok(applicationUserService.deleteUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
