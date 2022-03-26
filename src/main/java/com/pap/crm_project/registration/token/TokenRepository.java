package com.pap.crm_project.registration.token;

import com.pap.crm_project.entities.applicationuser.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
    void deleteByApplicationUser(ApplicationUser user);

    @Transactional
    @Modifying
    @Query("UPDATE Token rt " + "SET rt.confirmationTime = ?2 " + "WHERE rt.token = ?1")
    int updateConfirmationTime(String token, LocalDateTime confirmationTime);
}