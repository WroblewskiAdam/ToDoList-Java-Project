package com.example.PAP2022.services;

import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.RegistrationToken;
import com.example.PAP2022.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository registrationTokenRepository;

    public void saveRegistrationToken(RegistrationToken token) {
        registrationTokenRepository.save(token);
    }

    public Optional<RegistrationToken> getRegistrationToken(String token) {
        return registrationTokenRepository.findByToken(token);
    }

    public void setConfirmationTime(String token) {
        registrationTokenRepository.updateConfirmationTime(token, LocalDateTime.now());
    }

    public void deleteRegistrationToken(ApplicationUser applicationUser) {
        registrationTokenRepository.deleteByApplicationUser(applicationUser);
    }
}
