package com.pap.crm_project.task;

import com.pap.crm_project.applicationuser.ApplicationUser;
import com.pap.crm_project.team.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private LocalDateTime creationtime = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "giver_id"
    )
    private ApplicationUser giver;

    @ManyToMany
    @JoinColumn(
            nullable = false,
            name = "receiver_id"
    )
    private List<ApplicationUser> receivers;

    @ManyToOne
    @JoinColumn(
            name = "team_id"
    )
    private Team team;

    public Task(String title, String description, LocalDateTime deadline, TaskPriority priority, ApplicationUser giver, List<ApplicationUser> receivers) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.giver = giver;
        this.receivers = receivers;
    }
//    public Task(String title, String description, String deadlineString, TaskPriority priority, ApplicationUser giver, List<ApplicationUser> receivers) {
//        this.title = title;
//        this.description = description;
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        this.deadline = LocalDateTime.parse(deadlineString, formatter);
//
//        this.priority = priority;
//        this.giver = giver;
//        this.receivers = receivers;
//    }

}

