package com.example.PAP2022.repository;

import com.example.PAP2022.models.ApplicationUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationUserRepositoryTest {

    @Autowired
    private ApplicationUserRepository userRepository;

    @Test
    void shouldFindByEmail() {
        String email = "ogorek@gmail.com";
        assertSame(email, userRepository.findByEmail(email).get().getEmail());
    }

    @Test
    @Transactional
    void shouldDeleteByEmail() {
        String email = "ogorek@gmail.com";
        ApplicationUser user = userRepository.findByEmail(email).get();
        userRepository.deleteByEmail(email);
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    void shouldEnableApplicationUser() {
        ApplicationUser user = userRepository.findById(4L).get();
        userRepository.enableApplicationUser(user.getEmail());
        assertSame(true, userRepository.findById(4L).get().getEnabled());
    }
}