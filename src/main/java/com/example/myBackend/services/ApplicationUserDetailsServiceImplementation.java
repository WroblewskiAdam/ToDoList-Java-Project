package com.example.myBackend.services;

import com.example.myBackend.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myBackend.models.ApplicationUser;

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
}
