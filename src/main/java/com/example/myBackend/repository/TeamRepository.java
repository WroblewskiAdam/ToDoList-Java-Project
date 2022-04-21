package com.example.myBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.myBackend.models.Team;
import com.example.myBackend.models.ApplicationUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findTeamByName(String name);
    List<Team> findTeamByTeamLeader(ApplicationUser teamLeader);
    void deleteByName(String name);
}