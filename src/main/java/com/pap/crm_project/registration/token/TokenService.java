package com.pap.crm_project.registration.token;

import com.pap.crm_project.entities.applicationuser.ApplicationUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository registrationTokenRepository;

    public void saveRegistrationToken(Token token) {
        registrationTokenRepository.save(token);
    }

    public Optional<Token> getRegistrationToken(String token) {
        return registrationTokenRepository.findByToken(token);
    }

    public void setConfirmationTime(String token) {
        registrationTokenRepository.updateConfirmationTime(token, LocalDateTime.now());
    }

    public void deleteRegistrationToken(ApplicationUser applicationUser) {
        registrationTokenRepository.deleteByApplicationUser(applicationUser);
    }
}
