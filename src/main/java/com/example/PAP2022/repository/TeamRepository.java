package com.example.PAP2022.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.PAP2022.models.Team;
import com.example.PAP2022.models.ApplicationUser;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{
}