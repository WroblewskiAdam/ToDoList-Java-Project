package com.example.PAP2022.repository;

import com.example.PAP2022.models.ApplicationUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmailTokenRepositoryTest {

    @Autowired
    private EmailTokenRepository emailTokenRepository;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Test
    void shouldFindByToken() {
        String token = "token1";
        assertTrue(token.equals(emailTokenRepository.findByToken(token).get().getToken()));
    }

    @Test
    void shouldDeleteByApplicationUser() {
        ApplicationUser user = userRepository.findById(1L).get();
        emailTokenRepository.deleteByApplicationUser(user);
        assertFalse(emailTokenRepository.findById(1L).isPresent());
    }

    @Test
    void shouldUpdateConfirmationTime() {
        LocalDateTime confirmationTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        String token = "token1";
        emailTokenRepository.updateConfirmationTime(token, confirmationTime);
        assertEquals(confirmationTime, emailTokenRepository.findByToken(token).get().getConfirmationTime());
    }
}