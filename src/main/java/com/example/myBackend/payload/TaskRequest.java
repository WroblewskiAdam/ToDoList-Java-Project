package com.example.myBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

import com.example.myBackend.enums.TaskPriority;
import com.example.myBackend.models.ApplicationUser;
import com.example.myBackend.models.Team;


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

