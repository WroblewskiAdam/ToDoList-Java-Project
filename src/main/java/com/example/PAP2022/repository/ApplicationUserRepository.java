package com.example.PAP2022.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.PAP2022.models.ApplicationUser;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

  Optional<ApplicationUser> findByEmail(String email);
  void deleteByEmail(String email);


  @Transactional
  @Modifying
  @Query("UPDATE ApplicationUser au " + "SET au.enabled = TRUE WHERE au.email = ?1")
  int enableApplicationUser(String email);

}
