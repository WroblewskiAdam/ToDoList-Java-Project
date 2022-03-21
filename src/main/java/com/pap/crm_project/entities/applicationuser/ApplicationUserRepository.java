package com.pap.crm_project.entities.applicationuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE ApplicationUser au " + "SET au.enabled = TRUE WHERE au.email = ?1")
    int enableApplicationUser(String email);
}