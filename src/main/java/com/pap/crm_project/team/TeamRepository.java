package com.pap.crm_project.team;

import com.pap.crm_project.applicationuser.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findTeamByName(String name);
    List<Team> findTeamByTeamLeader(ApplicationUser teamLeader);
}