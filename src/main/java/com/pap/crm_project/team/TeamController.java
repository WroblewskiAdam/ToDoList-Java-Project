package com.pap.crm_project.team;

import com.pap.crm_project.applicationuser.ApplicationUser;
import com.pap.crm_project.applicationuser.ApplicationUserService;
import com.pap.crm_project.task.Task;
import com.pap.crm_project.task.TaskRequest;
import com.pap.crm_project.task.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final ApplicationUserService applicationUserService;

    public TeamController(TeamService teamService, ApplicationUserService applicationUserService) {
        this.teamService = teamService;
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/show_all")
    public String getAllTeams(Model model){
        List<Team> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);
        return "teams";
    }

    @GetMapping("/show_team_leader") // bo gdy "/show/team_leader to nie działa add Team
    public String getTeamsTeamLeader(@AuthenticationPrincipal ApplicationUser applicationUser, Model model){
        List<Team> teams = teamService.getTeamsTeamLeader(applicationUser);
        model.addAttribute("teams", teams);
        return "teams";
    }

    @GetMapping("/show_member")
    public String getTeamsMember(@AuthenticationPrincipal ApplicationUser applicationUser, Model model){
        List<Team> teams = applicationUserService.getApplicationUserById(applicationUser.getId()).get().getTeams();
        model.addAttribute("teams", teams);
        return "teams";
    }

    @GetMapping("/addNew/form")
    public String createTeam(Model model){
        Team team = new Team();
        List<ApplicationUser> users = applicationUserService.getAllApplicationUsers();
        model.addAttribute("team", team);
        model.addAttribute("users", users);
        return "create_team";
    }

    @PostMapping("/addNew")
    public String addNewTeam(@AuthenticationPrincipal ApplicationUser applicationUser, @ModelAttribute("team") Team team) {
        team.setTeamLeader(applicationUser);
        boolean isTeamPresent = teamService.saveTeam(team);
        if (isTeamPresent) {
            return "redirect:/teams/addNew/form?team_exists";
        } else {
            return "redirect:/teams/show_all";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return "redirect:/teams/show/all";
    }
}
