package com.example.PAP2022.models;

import com.example.PAP2022.enums.TaskPriority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private LocalDateTime creationTime = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean ticked = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "giver_id"
    )
    private ApplicationUser giver;

    @ManyToMany()
    @JoinTable(
            name = "tasks_receivers",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "application_user_id")
    )
    private List<ApplicationUser> receivers;

    @ManyToOne()
    @JoinColumn(
            name = "team_id"
    )
    private Team team;

    public Task(String title, String description, LocalDateTime deadline, TaskPriority priority, ApplicationUser giver, List<ApplicationUser> receivers, Team team) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.giver = giver;
        this.receivers = receivers;
        this.team = team;
    }

    @Override
    public String toString() {
        return  title;
    }
}


