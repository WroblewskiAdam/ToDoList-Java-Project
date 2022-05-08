package com.example.PAP2022.repository;

import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public interface TokenRepository extends JpaRepository<RegistrationToken, Long> {

    Optional<RegistrationToken> findByToken(String token);
    Optional<RegistrationToken> findByApplicationUser(String token);
    void deleteByApplicationUser(ApplicationUser user);

    @Transactional
    @Modifying
    @Query("UPDATE RegistrationToken rt " + "SET rt.confirmationTime = ?2 " + "WHERE rt.token = ?1")
    int updateConfirmationTime(String token, LocalDateTime confirmationTime);
}
