package com.example.myBackend.controllers;

import com.example.myBackend.enums.ApplicationUserRole;
import com.example.myBackend.models.ApplicationUser;
import com.example.myBackend.payload.AuthResponse;
import com.example.myBackend.payload.LoginRequest;
import com.example.myBackend.payload.SignUpRequest;
import com.example.myBackend.repository.ApplicationUserRepository;
import com.example.myBackend.security.jwt.JwtUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final ApplicationUserRepository applicationUserRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private final JwtUnit jwtUnit;

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest){
        if(applicationUserRepository.existsByEmail(signUpRequest.getEmail())){
            log.error("User not registred");
            return ResponseEntity.badRequest().body("Error: User with this email already exist!");
        }

        ApplicationUser applicationUser = new ApplicationUser(
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getImg(), ApplicationUserRole.USER);

        applicationUserRepository.save(applicationUser);
        log.info("User registred");
        return ResponseEntity.ok("User successfully registred!)");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUnit.createToken(authentication);

//        ApplicationUser applicationUser = (ApplicationUser) authentication.getPrincipal();
        log.info("User logined");
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
