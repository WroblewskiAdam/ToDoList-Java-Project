package com.example.PAP2022.services;

import com.example.PAP2022.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PAP2022.models.ApplicationUser;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsServiceImplementation implements UserDetailsService {
  private final ApplicationUserRepository applicationUserRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    ApplicationUser user = applicationUserRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));

    return ApplicationUserDetailsImplementation.build(user);
  }

  public Optional<ApplicationUser> getApplicationUserById(Long id) {
    return applicationUserRepository.findById(id);
  }
  public Optional<ApplicationUser> loadApplicationUserById(Long id) {
    return applicationUserRepository.findById(id);
  }
}
