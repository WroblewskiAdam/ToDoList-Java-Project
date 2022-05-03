package com.example.PAP2022.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

import com.example.PAP2022.enums.TaskPriority;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    private String title;
    private String description;
    private String deadline;
    @Enumerated(EnumType.STRING)
    private String priority;
    private Long giverId;
    private List<Long> receiversIds;
//    jeśli team == 0 => zadanie nie jest przypisane do żadnego zespołu
    private Long teamId;
}


