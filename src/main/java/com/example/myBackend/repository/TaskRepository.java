package com.example.myBackend.repository;

import com.example.myBackend.models.Task;
import com.example.myBackend.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByGiver(ApplicationUser giver);
}
