package com.example.PAP2022.controllers;

import com.example.PAP2022.exceptions.TaskNotFoundException;
import com.example.PAP2022.exceptions.TeamNotFoundException;
import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.payload.TaskRequest;
import com.example.PAP2022.services.ApplicationUserDetailsService;
import com.example.PAP2022.services.TaskService;
import com.example.PAP2022.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ApplicationUserDetailsService applicationUserService;
    private final TeamService teamService;

    @PutMapping("/edit")
    public ResponseEntity<?> editTask(@RequestParam Long taskId, @RequestBody TaskRequest taskRequest) {
        try {
            return ResponseEntity.ok().body(taskService.editTask(taskId, taskRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveTask(@RequestBody TaskRequest request) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/tasks/save").toUriString());
            return ResponseEntity.created(uri).body(taskService.saveTask(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestParam Long taskId){
        if (taskService.loadTaskById(taskId).isPresent()) {
            return ResponseEntity.ok().body(taskService.deleteTask(taskId));
        } else {
            return ResponseEntity.badRequest().body(
                    new TaskNotFoundException(
                            "Could not find task with ID " + taskId).getMessage());
        }
    }

    @PutMapping("/tick")
    public ResponseEntity<?> tickTask(@RequestParam Long taskId, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok().body(taskService.tickTask(taskId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks(){
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @GetMapping("/get_receivers")
    public ResponseEntity<?> getReceivers(@RequestParam Long taskId){
        if(taskService.loadTaskById(taskId).isPresent()){
            return ResponseEntity.ok().body(taskService.getTaskReceivers(taskId));
        }
        else{
            return ResponseEntity.badRequest().body(
                    new TaskNotFoundException("Could not find task with ID " + taskId).getMessage());
        }
    }

    @GetMapping("/get_receivers_who_done")
    public ResponseEntity<?> getTaskReceiversWhoDone(@RequestParam Long taskId){
        if(taskService.loadTaskById(taskId).isPresent()){
            return ResponseEntity.ok().body(taskService.getTaskReceiversWhoDone(taskId));
        }
        else{
            return ResponseEntity.badRequest().body(
                    new TaskNotFoundException("Could not find task with ID " + taskId).getMessage());
        }
    }

    // Filtrowanie po userze
    @GetMapping("/today")
    public ResponseEntity<?> getTodayTasks(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getTodayTasks(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @GetMapping("/today_given")
    public ResponseEntity<?> getTodayTasksGiven(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getTodayTasksGiven(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @GetMapping("/seven_days")
    public ResponseEntity<?> getSevenDaysTasks(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getSevenDaysTasks(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @GetMapping("/seven_days_given")
    public ResponseEntity<?>getSevenDaysTasksGiven(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getSevenDaysTasksGiven(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @GetMapping("/private")
    public ResponseEntity<?> getPrivateTasks(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getPrivateTasks(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @GetMapping("/private_given")
    public ResponseEntity<?> getPrivateTasksGiven(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getPrivateTasksGiven(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @GetMapping("/received")
    public ResponseEntity<?> getReceivedTasks(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getReceivedTasks(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @GetMapping("/given")
    public ResponseEntity<?> getGivenTasks(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getGivenTasks(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @GetMapping("/expired")
    public ResponseEntity<?> getExpiredTasks(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getExpiredTasks(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @GetMapping("/expired_given")
    public ResponseEntity<?> getExpiredTasksGiven(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getExpiredTasksGiven(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    @GetMapping("/done_given")
    public ResponseEntity<?> getDoneGivenTasks(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getDoneGivenTasks(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }
    // z punktu widzenia użytkownika któremu zostało zlecone zadanie
    @GetMapping("/done_received")
    public ResponseEntity<?> getDoneReceivedTasks(@RequestParam Long id) {
        if (applicationUserService.loadApplicationUserById(id).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getDoneReceivedTasks(applicationUserService.loadApplicationUserById(id).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find user with ID " + id).getMessage());
        }
    }

    //Filtrowanie po Teamie
    @GetMapping("/team/today")
    public ResponseEntity<?> getTodayTasksTeam(@RequestParam Long teamid) {
        if (teamService.loadTeamById(teamid).isPresent()){
            return ResponseEntity.ok().body(
                    taskService.getTodayTasksTeam(teamService.loadTeamById(teamid).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new TeamNotFoundException("Could not find team with ID " + teamid).getMessage());
        }
    }

    @GetMapping("/team/today_given")
    public ResponseEntity<?> getTodayTasksGivenTeam(@RequestParam Long teamid, @RequestParam Long userid ) {
        if (teamService.loadTeamById(teamid).isPresent() && applicationUserService.loadApplicationUserById(userid).isPresent()){
            return ResponseEntity.ok().body(
                    taskService.getTodayTasksGivenTeam(teamService.loadTeamById(teamid).get(),applicationUserService.loadApplicationUserById(userid).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new TeamNotFoundException("Could not find team with ID " + teamid).getMessage());
        }
    }

    @GetMapping("/team/seven_days")
    public ResponseEntity<?> getSevenDaysTasksTeam(@RequestParam Long teamid) {
        if (teamService.loadTeamById(teamid).isPresent()) {
            return ResponseEntity.ok().body(
                    taskService.getSevenDaysTasksTeam(teamService.loadTeamById(teamid).get()));
        } else {
            return ResponseEntity.badRequest().body(
                    new UserNotFoundException("Could not find team with ID " + teamid).getMessage());
        }
    }

}
