package com.example.PAP2022.controllers;


import javax.validation.Valid;

import com.example.PAP2022.enums.ApplicationUserRole;
import com.example.PAP2022.models.ApplicationUserDetails;
import com.example.PAP2022.services.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.payload.LoginRequest;
import com.example.PAP2022.payload.ApplicationUserRequest;
import com.example.PAP2022.payload.AuthResponse;
import com.example.PAP2022.repository.ApplicationUserRepository;
import com.example.PAP2022.security.jwt.JwtUnit;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final RegistrationService registrationService;
    private final JwtUnit jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        ApplicationUserDetails userDetails = (ApplicationUserDetails) authentication.getPrincipal();
        if (userDetails.isEnabled()) {
            String role = userDetails.getAuthorities().toString();

            log.info("user logged :)");

            return ResponseEntity.ok(
                    new AuthResponse(
                            jwt,
                            userDetails.getId(),
                            userDetails.getEmail(),
                            role
                    )
            );
        } else {
            return ResponseEntity.badRequest().body("Account is not confirmed");
        }
    }

//    TODO jedna wersja jest do rejestracji za pomocą postamana
    @PostMapping("/registration_postman")
    public ResponseEntity<?> registerUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("image") MultipartFile file) {

        ApplicationUserRequest signUpRequest = new ApplicationUserRequest(firstName, lastName, email, password, file);
        try {
            registrationService.register(signUpRequest);
            return ResponseEntity.ok("User is registered");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    TODO druga wersja jest do rejestracji za pomocą przeglądarki
    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ApplicationUserRequest signUpRequest) {
        try {
            registrationService.register(signUpRequest);
            return ResponseEntity.ok("User is registered");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/confirmation")
    public ResponseEntity<?> confirmation(@RequestParam("token") String registrationToken) {
        try {
            registrationService.confirmToken(registrationToken);
            return ResponseEntity.ok("Email has been confirmed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
