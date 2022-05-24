package com.example.PAP2022.services;

import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.EmailToken;
import com.example.PAP2022.repository.EmailTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailTokenService {

    private final EmailTokenRepository emailTokenRepository;

    public void saveEmailToken(EmailToken token) {
        emailTokenRepository.save(token);
    }

    public Optional<EmailToken> getEmailToken(String token) {
        return emailTokenRepository.findByToken(token);
    }

    public void setConfirmationTime(String token) {
        emailTokenRepository.updateConfirmationTime(token, LocalDateTime.now());
    }

    public void deleteEmailToken(ApplicationUser applicationUser) {
        emailTokenRepository.deleteByApplicationUser(applicationUser);
    }
}
