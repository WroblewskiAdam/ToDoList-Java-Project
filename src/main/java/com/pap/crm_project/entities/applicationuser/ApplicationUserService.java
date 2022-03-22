package com.pap.crm_project.entities.applicationuser;

import com.pap.crm_project.registration.token.RegistrationToken;
import com.pap.crm_project.registration.token.RegistrationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;
    private final RegistrationTokenService registrationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return applicationUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                                String.format("User with this email %s was not found", email)));
    }

    public String signUpUser(ApplicationUser applicationUser) {
        boolean exists = applicationUserRepository
                .findByEmail(applicationUser.getEmail())
                .isPresent();

        if (exists) {
            ApplicationUser existingUser = applicationUserRepository.findByEmail(applicationUser.getEmail()).get();
            if (!existingUser.getEnabled()) {
                registrationTokenService.deleteRegistrationToken(existingUser);
                applicationUserRepository.deleteByEmail(existingUser.getEmail());
            }
            else {
                return "emailTaken";
            }
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(applicationUser.getPassword());

        applicationUser.setPassword(encodedPassword);
        applicationUserRepository.save(applicationUser);

        String token = UUID.randomUUID().toString();

        RegistrationToken registrationToken = new RegistrationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                applicationUser
        );

        registrationTokenService.saveRegistrationToken(registrationToken);
        return token;
    }

    public int enableApplicationUser(String email) {
        return applicationUserRepository.enableApplicationUser(email);
    }
}

