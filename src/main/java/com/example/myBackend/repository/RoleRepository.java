package com.example.myBackend.repository;

import java.util.Optional;

import com.example.myBackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myBackend.enums.ApplicationUserRole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ApplicationUserRole name);
}
