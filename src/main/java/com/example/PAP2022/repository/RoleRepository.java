package com.example.PAP2022.repository;

import java.util.Optional;

import com.example.PAP2022.enums.ApplicationUserRole;
import com.example.PAP2022.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PAP2022.enums.ApplicationUserRole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ApplicationUserRole name);
}
