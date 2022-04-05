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


import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/tasks/show_today")
    public String getTodayTasks(
            @AuthenticationPrincipal ApplicationUser applicationUser,
            Model model
    ) {
        List<Task> tasksList = taskService.getTodayTasks(applicationUser);
        model.addAttribute("tasks",tasksList);
        return "tasks";
    }

    @GetMapping("/tasks/show_today_given")
    public String getTodayTasksGiven(
            @AuthenticationPrincipal ApplicationUser applicationUser,
            Model model
    ) {
        List<Task> tasksList = taskService.getTodayTasksGiven(applicationUser);
        model.addAttribute("tasks",tasksList);
        return "tasks";
    }

    @GetMapping("/tasks/show_seven_days")
    public String getSevenDaysTasks(
            @AuthenticationPrincipal ApplicationUser applicationUser,
            Model model
    ) {
        List<Task> tasksList = taskService.getSevenDaysTasks(applicationUser);
        model.addAttribute("tasks",tasksList);
        return "tasks";
    }

    @GetMapping("/tasks/show_seven_days_given")
    public String getSevenDaysTasksGiven(
            @AuthenticationPrincipal ApplicationUser applicationUser,
            Model model
    ) {
        List<Task> tasksList = taskService.getSevenDaysTasksGiven(applicationUser);
        model.addAttribute("tasks",tasksList);
        return "tasks";
    }

    @GetMapping("/tasks/show_private")
    public String getPrivateTasks(
            @AuthenticationPrincipal ApplicationUser applicationUser,
            Model model
    ) {
        List<Task> tasksList = taskService.getPrivateTasks(applicationUser);
        model.addAttribute("tasks",tasksList);
        return "tasks";
    }

    @GetMapping("/tasks/show_private_given")
    public String getPrivateTasksGiven(
            @AuthenticationPrincipal ApplicationUser applicationUser,
            Model model
    ) {
        List<Task> tasksList = taskService.getPrivateTasksGiven(applicationUser);
        model.addAttribute("tasks",tasksList);
        return "tasks";
    }

    @GetMapping("/tasks/show_received")
    public String getReceivedTasks(
            @AuthenticationPrincipal ApplicationUser applicationUser,
            Model model
    ) {
        List<Task> tasksList = taskService.getReceivedTasks(applicationUser);
        model.addAttribute("tasks",tasksList);
        return "tasks";
    }

    @GetMapping("/tasks/show_given")
    public String getGivenTasks(
            @AuthenticationPrincipal ApplicationUser applicationUser,
            Model model
    ) {
        List<Task> tasksList = taskService.getGivenTasks(applicationUser);
        model.addAttribute("tasks",tasksList);
        return "tasks";
    }

    @GetMapping("/tasks/show_expired")
    public String getExpiredTasks(
            @AuthenticationPrincipal ApplicationUser applicationUser,
            Model model
    ) {
        List<Task> tasksList = taskService.getExpiredTasks(applicationUser);
        model.addAttribute("tasks",tasksList);
        return "tasks";
    }

    @GetMapping("/tasks/show_expired_given")
    public String getExpiredTasksGiven(
            @AuthenticationPrincipal ApplicationUser applicationUser,
            Model model
    ) {
        List<Task> tasksList = taskService.getExpiredTasksGiven(applicationUser);
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
        return "redirect:/tasks/show_all";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks/show_all";
    }

    @GetMapping("/tasks/tick/{id}")
    public String tickTask(@PathVariable Long id, @AuthenticationPrincipal ApplicationUser applicationUser, HttpServletRequest request) {
        Long app_user_id = applicationUser.getId();
        taskService.tickTask(id, app_user_id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
