package com.example.PAP2022.models;

import com.example.PAP2022.models.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "teamLeader_id"
    )
    private ApplicationUser teamLeader;

    @JsonIgnore
    @ManyToMany()
    @JoinTable(
            name = "teams_users",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "application_user_id")
    )
    private List<ApplicationUser> teamMembers;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "team"
    )
    private List<Task> teamTasks;

    public Team(String name, ApplicationUser teamLeader, List<ApplicationUser> teamMembers) {
        this.name = name;
        this.teamLeader = teamLeader;
        this.teamMembers = teamMembers;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addMemberToTeam(ApplicationUser member){
        this.teamMembers.add(member);
    }
}