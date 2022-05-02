package com.example.PAP2022.services;

import com.example.PAP2022.exceptions.TaskNotFoundException;
import com.example.PAP2022.exceptions.TeamNotFoundException;
import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.models.Task;
import com.example.PAP2022.models.Team;
import com.example.PAP2022.repository.TaskRepository;
import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.payload.TaskRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

        private TaskRepository taskRepository;
        private ApplicationUserDetailsServiceImplementation applicationUserService;
        private TeamService teamService;

        @Autowired
        public TaskService(TaskRepository taskRepository, ApplicationUserDetailsServiceImplementation applicationUserService, TeamService teamService) {
                this.taskRepository = taskRepository;
                this.applicationUserService = applicationUserService;
                this.teamService = teamService;
        }

        public Optional<Task> loadTaskById(Long taskId) {
                return taskRepository.findById(taskId);
        }

        public List<Task> getAllTasks(){
                return taskRepository.findAll();
        }

        public List<Task> getTodayTasks(ApplicationUser applicationUser) {
                return applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks().stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(1)))
                        .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
        }

        public List<Task> getTodayTasksGiven(ApplicationUser applicationUser) {
                return taskRepository.findByGiver(applicationUser).stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(1)))
                        .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
        }

        public List<Task> getSevenDaysTasks(ApplicationUser applicationUser) {
                return applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks().stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(7)))
                        .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
        }

        public List<Task> getSevenDaysTasksGiven(ApplicationUser applicationUser) {
                return taskRepository.findByGiver(applicationUser).stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(7)))
                        .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
        }

        public List<Task> getPrivateTasks(ApplicationUser applicationUser) {
                return applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks().stream()
                        .filter(task -> task.getTeam() == null)
                        .collect(Collectors.toList());
        }

        public List<Task> getPrivateTasksGiven(ApplicationUser applicationUser) {
                return taskRepository.findByGiver(applicationUser).stream()
                        .filter(task -> task.getTeam() == null)
                        .collect(Collectors.toList());
        }

        public List<Task> getReceivedTasks(ApplicationUser applicationUser) {
                return applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks();
        }

        public List<Task> getGivenTasks(ApplicationUser applicationUser){
                return taskRepository.findByGiver(applicationUser);
        }

        public List<Task> getExpiredTasks(ApplicationUser applicationUser){
                return applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks().stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
        }

        public List<Task> getExpiredTasksGiven(ApplicationUser applicationUser){
                return taskRepository.findByGiver(applicationUser).stream()
                        .filter(task -> task.getDeadline().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
        }

        public Long deleteTask(Long id) {
                taskRepository.deleteById(id);
                return id;
        }

        public Task saveTask(TaskRequest request) throws UserNotFoundException, TeamNotFoundException {
                ApplicationUser giver;
                List<ApplicationUser> receivers = new ArrayList<>();
                Team team;

                if (!applicationUserService.loadApplicationUserById(request.getGiverId()).isPresent()) {
                        throw new UserNotFoundException("Could not find user with ID " + request.getGiverId());
                } else {
                        giver = applicationUserService.loadApplicationUserById(request.getGiverId()).get();
                }

                for (Long id: request.getReceiversIds()) {
                        if (!applicationUserService.loadApplicationUserById(id).isPresent()) {
                                throw new UserNotFoundException("Could not find user with ID " + request.getGiverId());
                        } else {
                                receivers.add(applicationUserService.loadApplicationUserById(id).get());
                        }
                }

                if (!teamService.loadTeamById(request.getTeamId()).isPresent() && request.getTeamId() != 0) {
                        throw new TeamNotFoundException("Could not find team with ID " + request.getTeamId());
                } else {
                        if (request.getTeamId() != 0) {
                                team = teamService.loadTeamById(request.getTeamId()).get();
                                if (receivers.isEmpty()) {
                                        receivers.addAll(team.getTeamMembers());
                                }
                        } else {
                                team = null;
                        }
                }

                String date = request.getDeadline();
                date = date + ":00.0";
                LocalDateTime deadline = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);

                Task task = new Task(
                        request.getTitle(),
                        request.getDescription(),
                        deadline,
                        request.getPriority(),
                        giver,
                        receivers,
                        team
                );

                return taskRepository.save(task);
        }

        public Task tickTask(Long taskId, Long userId) throws UserNotFoundException, TaskNotFoundException {
                if (!applicationUserService.loadApplicationUserById(userId).isPresent()) {
                        throw new UserNotFoundException("Could not find user with ID " + userId);
                }

                if (!loadTaskById(taskId).isPresent()) {
                        throw new TaskNotFoundException("Could not find task with ID " + userId);
                }

                Task task = loadTaskById(taskId).get();
                List<Task> userTasks = applicationUserService.loadApplicationUserById(userId).get().getTasks();

                if(!userTasks.contains(task)) {
                        throw new TaskNotFoundException("Could not find task with ID " + userId + " in your tasks");
                }

                boolean isTicked = task.getTicked();
                task.setTicked(!isTicked);
                return taskRepository.save(task);
        }
}
