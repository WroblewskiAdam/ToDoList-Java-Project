package com.example.PAP2022.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequest {
    private String name;
    private Long teamLeaderId;
    private List<Long> membersIds;
}
