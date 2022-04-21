package com.example.myBackend.services;


import com.example.myBackend.models.Task;
import com.example.myBackend.repository.TaskRepository;
import com.example.myBackend.models.ApplicationUser;
import com.example.myBackend.payload.TaskRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

        private TaskRepository taskRepository;
        private ApplicationUserService applicationUserService;

        @Autowired
        public TaskService(TaskRepository taskRepository, ApplicationUserService applicationUserService) {
                this.taskRepository = taskRepository;
                this.applicationUserService = applicationUserService;
        }

        public List<Task> getAllTasks(){
                return taskRepository.findAll();
        }

        public List<Task> getTodayTasks(ApplicationUser applicationUser) {
                List<Task> tasks =  applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks().stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(1)))
                        .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
                return tasks;
        }

        public List<Task> getTodayTasksGiven(ApplicationUser applicationUser) {
                List<Task> tasks =  taskRepository.findByGiver(applicationUser).stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(1)))
                        .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
                return tasks;
        }

        public List<Task> getSevenDaysTasks(ApplicationUser applicationUser) {
                List<Task> tasks =  applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks().stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(7)))
                        .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
                return tasks;
        }

        public List<Task> getSevenDaysTasksGiven(ApplicationUser applicationUser) {
                List<Task> tasks =  taskRepository.findByGiver(applicationUser).stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(7)))
                        .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
                return tasks;
        }

        public List<Task> getPrivateTasks(ApplicationUser applicationUser) {
                List<Task> tasks =  applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks().stream()
                        .filter(task -> task.getTeam() == null)
                        .collect(Collectors.toList());
                return tasks;
        }

        public List<Task> getPrivateTasksGiven(ApplicationUser applicationUser) {
                List<Task> tasks =  taskRepository.findByGiver(applicationUser).stream()
                        .filter(task -> task.getTeam() == null)
                        .collect(Collectors.toList());
                return tasks;
        }

        public List<Task> getReceivedTasks(ApplicationUser applicationUser) {
                return applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks();
        }

        public List<Task> getGivenTasks(ApplicationUser applicationUser){
                List<Task> tasks =  taskRepository.findByGiver(applicationUser);
                return tasks;
        }

        public List<Task> getExpiredTasks(ApplicationUser applicationUser){
                List<Task> tasks = applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks().stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
                return tasks;
        }

        public List<Task> getExpiredTasksGiven(ApplicationUser applicationUser){
                List<Task> tasks =  taskRepository.findByGiver(applicationUser).stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
                return tasks;
        }

        // TODO Czy można nakładać różne filtry np. today, given, yellow ?
        // TODO trzeba się zastanowić jak robimy wykonanie zadania
        // TODO do zastanowienia także sposób sortowania dla teamu - czy identyczny ?
        // TODO sortowanie względem priorytetów

        public void deleteTask(Long id) {
                taskRepository.deleteById(id);
        }

        public void saveTask(TaskRequest request){
                String date = request.getDeadline();
                date = date + ":00.0";
                LocalDateTime deadline = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);

                Task task = new Task(
                        request.getTitle(),
                        request.getDescription(),
                        deadline,
                        request.getPriority(),
                        request.getGiver(),
                        request.getReceivers(),
                        request.getTeam()
                );

                taskRepository.save(task);
        }

        public void tickTask(Long task_id, Long app_user_id) {
                Task task = taskRepository.getById(task_id);
                List<Task> user_tasks = applicationUserService.getApplicationUserById(app_user_id).get().getTasks();
                if(user_tasks.contains(task)) {
                        boolean isTicked = task.getTicked();
                        task.setTicked(!isTicked);
                        taskRepository.save(task);

                }
        }
}
