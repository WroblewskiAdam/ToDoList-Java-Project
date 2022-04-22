package com.example.myBackend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.myBackend.models.ApplicationUser;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
  Optional<ApplicationUser> findByEmail(String email);
  Boolean existsByEmail(String email);
  void deleteByEmail(String email);
}
