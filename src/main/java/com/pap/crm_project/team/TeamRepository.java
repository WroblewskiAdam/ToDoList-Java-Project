package com.pap.crm_project.team;

import com.pap.crm_project.applicationuser.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
//    List<ApplicationUser> findByTeamLeader(ApplicationUser teamleader);
}