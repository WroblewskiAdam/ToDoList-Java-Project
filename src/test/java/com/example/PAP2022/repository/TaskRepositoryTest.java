package com.example.PAP2022.repository;

import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Test
    void shouldFindByGiver() {
        ApplicationUser user = userRepository.findById(1L).get();
        assertFalse(taskRepository.findByGiver(user).isEmpty());
        taskRepository.findByGiver(user).forEach(task -> assertSame(task.getGiver().getId(), user.getId()));
    }
}