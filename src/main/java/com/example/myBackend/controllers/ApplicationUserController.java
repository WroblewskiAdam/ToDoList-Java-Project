package com.example.myBackend.controllers;

import com.example.myBackend.models.ApplicationUser;
import com.example.myBackend.services.ApplicationUserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationUserController {
    @Autowired
    private final ApplicationUserService applicationUserService;

    @PostMapping("/user/save")
    public ResponseEntity<ApplicationUser> saveUser(@RequestBody ApplicationUser applicationUser){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(applicationUserService.saveApplicationUser(applicationUser));
    }

    @GetMapping("/users")
    public ResponseEntity<List<ApplicationUser>> getAllApplicationUsers(){
        return ResponseEntity.ok().body(applicationUserService.getAllApplicationUsers());
    }
}
