package com.example.PAP2022.controllers;

import com.example.PAP2022.payload.TaskRequest;
import com.example.PAP2022.services.TaskService;
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

    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks(){
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @GetMapping("/get_receivers")
    public ResponseEntity<?> getReceivers(@RequestParam Long taskId){
        try{
            return ResponseEntity.ok().body(taskService.getTaskReceivers(taskId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get_receivers_who_done")
    public ResponseEntity<?> getTaskReceiversWhoDone(@RequestParam Long taskId){
        try {
            return ResponseEntity.ok().body(taskService.getTaskReceiversWhoDone(taskId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


// User

    @GetMapping("/today")
    public ResponseEntity<?> getTodayTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getTodayTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/today_given")
    public ResponseEntity<?> getTodayTasksGiven(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getTodayTasksGiven(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/seven_days")
    public ResponseEntity<?> getSevenDaysTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getSevenDaysTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/seven_days_given")
    public ResponseEntity<?>getSevenDaysTasksGiven(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getSevenDaysTasksGiven(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/received")
    public ResponseEntity<?> getReceivedTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getReceivedTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/given")
    public ResponseEntity<?> getGivenTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getGivenTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/expired")
    public ResponseEntity<?> getExpiredTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getExpiredTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/expired_given")
    public ResponseEntity<?> getExpiredTasksGiven(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getExpiredTasksGiven(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/done_given")
    public ResponseEntity<?> getDoneGivenTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getDoneGivenTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // z punktu widzenia użytkownika któremu zostało zlecone zadanie
    @GetMapping("/done_received")
    public ResponseEntity<?> getDoneReceivedTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getDoneReceivedTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/check_if_task_is_done_by_user")
    public ResponseEntity<?> checkIfTaskIsDoneByUser(@RequestParam Long taskId, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok().body(taskService.checkIfTaskIsDoneByUser(taskId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


//    Private

    @GetMapping("/private")
    public ResponseEntity<?> getPrivateTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getPrivateTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/private_given")
    public ResponseEntity<?> getPrivateTasksGiven(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getPrivateTasksGiven(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/private_today")
    public ResponseEntity<?> getPrivateTodayTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getPrivateTodayTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/private_today_given")
    public ResponseEntity<?> getPrivateTodayTasksGiven(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getPrivateTodayTasksGiven(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/private_seven_days")
    public ResponseEntity<?> getPrivateSevenDaysTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getPrivateSevenDaysTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/private_seven_days_given")
    public ResponseEntity<?>getPrivateSevenDaysTasksGiven(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getPrivateSevenDaysTasksGiven(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/private_expired")
    public ResponseEntity<?> getPrivateExpiredTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getPrivateExpiredTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/private_expired_given")
    public ResponseEntity<?> getPrivateExpiredTasksGiven(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getPrivateExpiredTasksGiven(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/private_done_given")
    public ResponseEntity<?> getPrivateDoneGivenTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getPrivateDoneGivenTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // z punktu widzenia użytkownika któremu zostało zlecone zadanie
    @GetMapping("/private_done_received")
    public ResponseEntity<?> getPrivateDoneReceivedTasks(@RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(taskService.getPrivateDoneReceivedTasks(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


//    Team

    @GetMapping("/team_expired_all")
    public ResponseEntity<?> getAllExpiredTasksTeam(@RequestParam Long teamId) {
        try {
            return ResponseEntity.ok().body(taskService.getAllExpiredTasksTeam(teamId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team_done_all")
    public ResponseEntity<?> getAllDoneTasksTeam(@RequestParam Long teamId) {
        try {
            return ResponseEntity.ok().body(taskService.getAllDoneTasksTeam(teamId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team_received")
    public ResponseEntity<?> getReceivedTasksTeam(@RequestParam Long teamId, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok().body(taskService.getReceivedTasksTeam(teamId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team_given")
    public ResponseEntity<?> getGivenTasksTeam(@RequestParam Long teamId, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok().body(taskService.getGivenTasksTeam(teamId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team_expired")
    public ResponseEntity<?> getExpiredReceivedTasksTeam(@RequestParam Long teamId, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok().body(taskService.getReceivedExpiredTasksTeam(teamId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team_done")
    public ResponseEntity<?> getDoneTasksTeam(@RequestParam Long teamId, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok().body(taskService.getDoneTasksTeam(teamId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team_today")
    public ResponseEntity<?> getTodayTasksTeam(@RequestParam Long teamId) {
        try {
            return ResponseEntity.ok().body(taskService.getTodayTasksTeam(teamId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team_today_given")
    public ResponseEntity<?> getTodayTasksGivenTeam(@RequestParam Long teamId, @RequestParam Long userId ) {
        try {
            return ResponseEntity.ok().body(taskService.getTodayTasksGivenTeam(teamId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team_today_received")
    public ResponseEntity<?> getTodayTasksReceivedTeam(@RequestParam Long teamId, @RequestParam Long userId ) {
        try {
            return ResponseEntity.ok().body(taskService.getTodayTasksReceivedTeam(teamId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team_seven_days")
    public ResponseEntity<?> getSevenDaysTasksTeam(@RequestParam Long teamId) {
        try {
            return ResponseEntity.ok().body(taskService.getSevenDaysTasksTeam(teamId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team_seven_days_given")
    public ResponseEntity<?> getSevenDaysTasksGivenTeam(@RequestParam Long teamId, @RequestParam Long userId ) {
        try {
            return ResponseEntity.ok().body(taskService.getSevenDaysTasksGivenTeam(teamId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team_seven_days_received")
    public ResponseEntity<?> getSevenDaysReceivedTasksTeam(@RequestParam Long teamId, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok().body(taskService.getSevenDaysReceivedTasksTeam(teamId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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

    @PutMapping("/tick")
    public ResponseEntity<?> tickTask(@RequestParam Long taskId, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok().body(taskService.tickTask(taskId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestParam Long taskId){
        try {
            return ResponseEntity.ok().body(taskService.deleteTask(taskId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}