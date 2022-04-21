package com.example.myBackend.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import com.example.myBackend.repository.ApplicationUserRepository;
import com.example.myBackend.models.ApplicationUser;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {
    @Autowired
    private final ApplicationUserRepository applicationUserRepository;
//    private final PasswordEncoder passwordEncoder;

    public Optional<ApplicationUser> getApplicationUserById(Long id) {
        return applicationUserRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return applicationUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                                String.format("User with this email %s was not found", email)));
    }

    public Optional<ApplicationUser> loadApplicationUserById(Long id) {
        return applicationUserRepository.findById(id);
    }

    public ApplicationUser saveApplicationUser(ApplicationUser applicationUser){
//        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        return applicationUserRepository.save(applicationUser);
    }

    public List<ApplicationUser> getAllApplicationUsers(){
        return applicationUserRepository.findAll();
    }
}

