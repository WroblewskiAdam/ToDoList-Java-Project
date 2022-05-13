package com.example.PAP2022.controllers;


import javax.validation.Valid;

import com.example.PAP2022.models.ApplicationUserDetails;
import com.example.PAP2022.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.PAP2022.payload.LoginRequest;
import com.example.PAP2022.payload.ApplicationUserRequest;
import com.example.PAP2022.payload.AuthResponse;
import com.example.PAP2022.security.jwt.JwtUnit;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
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

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("image") MultipartFile file) {

        ApplicationUserRequest signUpRequest = new ApplicationUserRequest(firstName, lastName, email, password, file);
        try {
            authService.register(signUpRequest);
            return ResponseEntity.ok("User is registered");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/confirmation")
    public ResponseEntity<?> confirmation(@RequestParam("token") String token) {
        try {
            authService.confirmApplicationUser(token);
            return ResponseEntity.ok("Email has been confirmed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset_password_email")
    public ResponseEntity<?> sendResetPasswordEmail(@RequestParam("email") String email) {
        try {
            authService.sendResetPasswordEmail(email);
            return ResponseEntity.ok("Email has just been sent");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/reset_password_token")
    public ResponseEntity<?> getResetPasswordToken(@RequestParam("token") String token) {
        try {
            return ResponseEntity.ok(authService.getApplicationUserByResetPasswordToken(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO trzeba zaszyfrować hasło już w części webowej !!!!!
    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@RequestParam("password") String password, @RequestParam("id") Long id) {
        try {
            authService.resetApplicationUserPassword(password, id);
            return ResponseEntity.ok("Password has been changed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
