package com.example.PAP2022.controllers;

import com.example.PAP2022.models.Role;
import com.example.PAP2022.payload.RoleRequest;
import com.example.PAP2022.services.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllRoles(){
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRole(@RequestBody RoleRequest request) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/save").toUriString());
            return ResponseEntity.created(uri).body(roleService.saveRole(new Role(request.getName())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}