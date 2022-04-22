package com.example.myBackend.controllers;


import java.util.List;
import javax.validation.Valid;

import com.example.myBackend.enums.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.myBackend.models.ApplicationUser;
import com.example.myBackend.payload.LoginRequest;
import com.example.myBackend.payload.SignUpRequest;
import com.example.myBackend.payload.AuthResponse;
import com.example.myBackend.repository.ApplicationUserRepository;
import com.example.myBackend.security.jwt.JwtUnit;
import com.example.myBackend.services.ApplicationUserDetailsImplementation;

@RestController
@Slf4j
@AllArgsConstructor
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final ApplicationUserRepository userRepository;
  private final PasswordEncoder encoder;
  private final JwtUnit jwtUtils;

  @PostMapping("/api/auth/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    ApplicationUserDetailsImplementation userDetails = (ApplicationUserDetailsImplementation) authentication.getPrincipal();
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
  }

  @PostMapping("/api/auth/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body("Ups! Email is already in use :)");
    }

    ApplicationUser user = new ApplicationUser(
            signUpRequest.getFirstName(),
            signUpRequest.getLastName(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()),
            signUpRequest.getImg(),
            ApplicationUserRole.USER
    );

    userRepository.save(user);

    return ResponseEntity.ok("User registered successfully!");
  }

    @GetMapping("/users")
    public ResponseEntity<List<ApplicationUser>>getUsers() {
      return ResponseEntity.ok().body(userRepository.findAll());
    }
}
