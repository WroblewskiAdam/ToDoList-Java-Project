package com.example.PAP2022.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.PAP2022.models.Team;
import com.example.PAP2022.models.ApplicationUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findTeamByName(String name);
    List<Team> findTeamByTeamLeader(ApplicationUser teamLeader);
    void deleteByName(String name);
}