package com.example.PAP2022.controllers;

import com.example.PAP2022.models.Task;
import com.example.PAP2022.payload.TaskRequest;
import com.example.PAP2022.services.ApplicationUserDetailsServiceImplementation;
import com.example.PAP2022.services.TaskService;
import com.example.PAP2022.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ApplicationUserDetailsServiceImplementation applicationUserService;
    private final TeamService teamService;

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @GetMapping("/today")
    public ResponseEntity<List<Task>> getTodayTasks(@RequestParam Long id) {
        return ResponseEntity.ok().body(taskService.getTodayTasks(applicationUserService.getApplicationUserById(id)));
    }

    @GetMapping("/today_given")
    public ResponseEntity<List<Task>> getTodayTasksGiven(@RequestParam Long id) {
        return ResponseEntity.ok().body(taskService.getTodayTasksGiven(applicationUserService.getApplicationUserById(id)));
    }

    @GetMapping("/seven_days")
    public ResponseEntity<List<Task>> getSevenDaysTasks(@RequestParam Long id) {
        return ResponseEntity.ok().body(taskService.getSevenDaysTasks(applicationUserService.getApplicationUserById(id)));
    }

    @GetMapping("/seven_days_given")
    public ResponseEntity<List<Task>> getSevenDaysTasksGiven(@RequestParam Long id) {
        return ResponseEntity.ok().body(taskService.getSevenDaysTasksGiven(applicationUserService.getApplicationUserById(id)));
    }

    @GetMapping("/private")
    public ResponseEntity<List<Task>> getPrivateTasks(@RequestParam Long id) {
        return ResponseEntity.ok().body(taskService.getPrivateTasks(applicationUserService.getApplicationUserById(id)));
    }

    @GetMapping("/private_given")
    public ResponseEntity<List<Task>> getPrivateTasksGiven(@RequestParam Long id) {
        return ResponseEntity.ok().body(taskService.getPrivateTasksGiven(applicationUserService.getApplicationUserById(id)));
    }

    @GetMapping("/received")
    public ResponseEntity<List<Task>> getReceivedTasks(@RequestParam Long id) {
        return ResponseEntity.ok().body(taskService.getReceivedTasks(applicationUserService.getApplicationUserById(id)));
    }

    @GetMapping("/given")
    public ResponseEntity<List<Task>> getGivenTasks(@RequestParam Long id) {
        return ResponseEntity.ok().body(taskService.getGivenTasks(applicationUserService.getApplicationUserById(id)));
    }

    @GetMapping("/expired")
    public ResponseEntity<List<Task>> getExpiredTasks(@RequestParam Long id) {
        return ResponseEntity.ok().body(taskService.getExpiredTasks(applicationUserService.getApplicationUserById(id)));
    }

    @GetMapping("/expired_given")
    public ResponseEntity<List<Task>> getExpiredTasksGiven(@RequestParam Long id) {
        return ResponseEntity.ok().body(taskService.getExpiredTasksGiven(applicationUserService.getApplicationUserById(id)));
    }

    // TODO Jak to widzicie?

//    @GetMapping("/addTasks")
//    public String createTask(Model model){
//        TaskRequest taskRequest = new TaskRequest();
//        List<ApplicationUser> users = applicationUserService.getAllApplicationUsers();
//        List<Team> teams = teamService.getAllTeams();
//        model.addAttribute("taskRequest", taskRequest);
//        model.addAttribute("users", users);
//        model.addAttribute("teams", teams);
//        return "create_task";
//    }

    @PostMapping("/save")
    public ResponseEntity saveTask(@RequestBody TaskRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/tasks/save").toUriString());
        return ResponseEntity.created(uri).body(taskService.saveTask(request));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteTask(@RequestParam("id") Long id){
        try{
            return ResponseEntity.ok().body(taskService.deleteTask(id));
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO Jak to przerobić

//    @GetMapping("/tasks/tick/{id}")
//    public String tickTask(@PathVariable Long id, @AuthenticationPrincipal ApplicationUser applicationUser, HttpServletRequest request) {
//        Long app_user_id = applicationUser.getId();
//        taskService.tickTask(id, app_user_id);
//        String referer = request.getHeader("Referer");
//        return "redirect:" + referer;
//    }
}
