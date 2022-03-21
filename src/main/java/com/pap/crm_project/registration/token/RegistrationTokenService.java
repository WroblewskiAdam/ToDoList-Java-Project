package com.pap.crm_project.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationTokenService {

    private final RegistrationTokenRepository registrationTokenRepository;

    public void saveRegistrationToken(RegistrationToken token) {
        registrationTokenRepository.save(token);
    }

    public Optional<RegistrationToken> getRegistrationToken(String token) {
        return registrationTokenRepository.findByToken(token);
    }

    public void setConfirmationTime(String token) {
        registrationTokenRepository.updateConfirmationTime(token, LocalDateTime.now());
    }
}
