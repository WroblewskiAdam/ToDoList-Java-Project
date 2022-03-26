package com.pap.crm_project.team;

import com.pap.crm_project.applicationuser.ApplicationUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "teamLeader_id"
    )
    private ApplicationUser teamLeader;

    public Team(String name, ApplicationUser teamLeader) {
        this.name = name;
        this.teamLeader = teamLeader;
    }
}