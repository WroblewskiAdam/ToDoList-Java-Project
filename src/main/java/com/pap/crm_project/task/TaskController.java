package com.pap.crm_project.task;

import com.pap.crm_project.applicationuser.ApplicationUser;
import com.pap.crm_project.applicationuser.ApplicationUserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final ApplicationUserService applicationUserService;

    public TaskController(TaskService taskService, ApplicationUserService applicationUserService) {
        this.taskService = taskService;
        this.applicationUserService = applicationUserService;
    }

//    handler method to handle lis students request and return mode and view
    @GetMapping("/tasks/showAll")
    public String listTasks(Model model){
        List<Task> tasksList = taskService.getAllTasks();
        model.addAttribute("tasks",tasksList);
        return "tasks";
    }

    @GetMapping("/tasks/addNew/form")
    public String createTask(Model model){
        TaskRequest taskRequest = new TaskRequest();
        List<ApplicationUser> users = applicationUserService.getAllApplicationUsers();
        model.addAttribute("taskRequest", taskRequest);
        model.addAttribute("users", users);
        return "create_task";
    }

    @PostMapping("/tasks/addNew")
    public String addNewTask(@AuthenticationPrincipal ApplicationUser applicationUser, @ModelAttribute("task") TaskRequest request) {
        request.setGiver(applicationUser);
        taskService.saveTask(request);
        return "redirect:/tasks/showAll";
    }


//
//
//    @GetMapping("/tasks")
//        public String listTasks(Model model){
//        List<Task> tasksList = taskService.getTaskById(2L);
//        model.addAttribute("tasks",tasksList);
//        return "tasks";
//    }
//
//
//    @GetMapping("/find")
//    public List<Task> getTasksById(){
//        return taskService.getTaskById(1L);
//    }
//
//    @GetMapping("/tasks/addNew")
//    public String createTask(Model model){
//        Task task = new Task();
//        model.addAttribute("task", task);
//        return "create_task";
//    }
//
//    @PostMapping("/tasks/addNew")
//    public String addNewTask(@ModelAttribute("task") Task task){
//        taskService.saveTask(task);
//        return "redirect:/tasks";
//    }

}
