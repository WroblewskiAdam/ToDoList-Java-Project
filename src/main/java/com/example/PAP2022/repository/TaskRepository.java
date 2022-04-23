package com.example.PAP2022.repository;

import com.example.PAP2022.models.Task;
import com.example.PAP2022.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByGiver(ApplicationUser giver);
}
