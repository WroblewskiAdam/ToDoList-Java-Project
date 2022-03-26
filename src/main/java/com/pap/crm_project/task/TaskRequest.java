package com.pap.crm_project.task;

import com.pap.crm_project.applicationuser.ApplicationUser;
import com.pap.crm_project.team.Team;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class TaskRequest {

    private String title;
    private String description;
    private String deadline;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    private ApplicationUser giver;
    private List<ApplicationUser> receivers;
    private Team team;
}

