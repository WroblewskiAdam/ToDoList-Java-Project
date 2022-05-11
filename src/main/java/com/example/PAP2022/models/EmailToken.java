package com.example.PAP2022.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class EmailToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime creatingTime;
    private LocalDateTime expiringTime;
    private LocalDateTime confirmationTime;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "application_user_id"
    )
    private ApplicationUser applicationUser;

    public EmailToken(String token,
                      LocalDateTime creatingTime,
                      LocalDateTime expiringTime,
                      ApplicationUser applicationUser) {
        this.token = token;
        this.creatingTime = creatingTime;
        this.expiringTime = expiringTime;
        this.applicationUser = applicationUser;
    }
}
