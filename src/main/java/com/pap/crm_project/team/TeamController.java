package com.pap.crm_project.team;

import com.pap.crm_project.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams")
    public List<Team> getListTeams(){
        return teamService.getAllTeams();
    }

    @PostMapping("/teams/addNew")
    public void addTask(@RequestBody Team team) {
        teamService.saveTeam(team);
    }
}
