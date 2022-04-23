package com.example.PAP2022.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

import com.example.PAP2022.enums.TaskPriority;
import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.Team;


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

