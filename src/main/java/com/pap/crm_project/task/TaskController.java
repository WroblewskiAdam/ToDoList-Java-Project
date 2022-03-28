package com.pap.crm_project.task;

import com.pap.crm_project.applicationuser.ApplicationUser;
import com.pap.crm_project.applicationuser.ApplicationUserService;
import com.pap.crm_project.team.Team;
import com.pap.crm_project.team.TeamService;
import org.hibernate.annotations.OnDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final ApplicationUserService applicationUserService;
    private final TeamService teamService;

    @Autowired
    public TaskController(TaskService taskService, ApplicationUserService applicationUserService, TeamService teamService) {
        this.taskService = taskService;
        this.applicationUserService = applicationUserService;
        this.teamService = teamService;
    }

    @GetMapping("/tasks/show_all")
    public String getAllTasks(Model model){
        List<Task> tasksList = taskService.getAllTasks();
        model.addAttribute("tasks",tasksList);
        return "tasks";
    }

    @GetMapping("/tasks/addNew/form")
    public String createTask(Model model){
        TaskRequest taskRequest = new TaskRequest();
        List<ApplicationUser> users = applicationUserService.getAllApplicationUsers();
        List<Team> teams = teamService.getAllTeams();
        model.addAttribute("taskRequest", taskRequest);
        model.addAttribute("users", users);
        model.addAttribute("teams", teams);
        return "create_task";
    }

    @PostMapping("/tasks/addNew")
    public String addNewTask(@AuthenticationPrincipal ApplicationUser applicationUser, @ModelAttribute("task") TaskRequest request) {
        request.setGiver(applicationUser);
        taskService.saveTask(request);
        return "redirect:/tasks/showAll";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks/showAll";
    }

}
