package com.example.PAP2022.repository;

import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {

    Optional<EmailToken> findByToken(String token);
    void deleteByApplicationUser(ApplicationUser user);

    @Transactional
    @Modifying
    @Query("UPDATE EmailToken et " + "SET et.confirmationTime = ?2 " + "WHERE et.token = ?1")
    int updateConfirmationTime(String token, LocalDateTime confirmationTime);
}
