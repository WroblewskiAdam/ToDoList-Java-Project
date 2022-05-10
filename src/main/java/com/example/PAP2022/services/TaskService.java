package com.example.PAP2022.services;

import com.example.PAP2022.enums.TaskPriority;
import com.example.PAP2022.exceptions.*;
import com.example.PAP2022.models.Task;
import com.example.PAP2022.models.Team;
import com.example.PAP2022.repository.TaskRepository;
import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.payload.TaskRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ApplicationUserDetailsService applicationUserService;
    private final TeamService teamService;

    @Autowired
    public TaskService(TaskRepository taskRepository, ApplicationUserDetailsService applicationUserService, TeamService teamService) {
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

    public List<ApplicationUser> getTaskReceivers(Long taskId){
        return taskRepository.findById(taskId).get().getReceivers();
    }

    public List<ApplicationUser> getTaskReceiversWhoDone(Long taskId){
        return taskRepository.findById(taskId).get().getReceiversWhoDone();
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

    public List<Task> getDoneGivenTasks(ApplicationUser applicationUser) {
        return applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks().stream()
                .filter(task -> task.getGiver() == applicationUser)
                .filter(Task::getIsDone)
                .collect(Collectors.toList());
    }

    // z punktu widzenia użytkownika któremu zostało zlecone zadanie
    public List<Task> getDoneReceivedTasks(ApplicationUser applicationUser) {
        return applicationUserService.loadApplicationUserById(applicationUser.getId()).get().getTasks().stream()
                .filter(task -> task.getReceiversWhoDone().contains(applicationUser))
                .collect(Collectors.toList());
    }

    // ############### Team filters ##################

    public List<Task> getTodayTasksTeam(Team team) {
        return team.getTeamTasks().stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(1)))
                .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<Task> getTodayTasksGivenTeam(Team team, ApplicationUser user) {
        List<Task> teamTasks = new ArrayList<>(team.getTeamTasks());
        List<Task> userTasks = new ArrayList<>(taskRepository.findByGiver(user));
        userTasks.retainAll(teamTasks);
        return userTasks.stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(1)))
                .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<Task> getSevenDaysTasksTeam(Team team) {
        return team.getTeamTasks().stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(7)))
                .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }


    public Long deleteTask(Long id) {
        taskRepository.deleteById(id);
        return id;
    }

    public LocalDateTime convertDeadline(String deadlineString) throws InvalidDeadlineFormatException, InvalidDeadlineDateException {
        LocalDateTime deadline;

        // TODO zastanowić się jak ogarnąć rzucanie wyjątku gdy format jest zły
        deadlineString = deadlineString + ":00.0";
        deadline = LocalDateTime.parse(deadlineString, DateTimeFormatter.ISO_DATE_TIME);

        if (deadline.isBefore(LocalDateTime.now())){
            throw new InvalidDeadlineDateException("Cannot determine the deadline in the past ");
        }

        return deadline;
    }

    public Task editTask(Long taskId, TaskRequest taskRequest) throws
            UserNotFoundException,
            TaskNotFoundException,
            InvalidDeadlineFormatException,
            InvalidDeadlineDateException,
            InvalidTaskPriorityException {

        if (loadTaskById(taskId).isPresent()) {
            Task task = loadTaskById(taskId).get();
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setDeadline(convertDeadline(taskRequest.getDeadline()));
            task.setPriority(TaskPriority.parse(taskRequest.getPriority()));
            task.setReceivers(applicationUserService.getUsersByIds(taskRequest.getReceiversIds()));

            return taskRepository.save(task);

        } else {
            throw new TaskNotFoundException("Could not find task with ID " + taskId);
        }

    }

    public Task saveTask(TaskRequest request) throws
            UserNotFoundException,
            TeamNotFoundException,
            InvalidDeadlineFormatException,
            InvalidDeadlineDateException, InvalidTaskPriorityException {

        ApplicationUser giver;
        Team team;

        if (!applicationUserService.loadApplicationUserById(request.getGiverId()).isPresent()) {
            throw new UserNotFoundException("Could not find user with ID " + request.getGiverId());
        } else {
            giver = applicationUserService.loadApplicationUserById(request.getGiverId()).get();
        }

        LocalDateTime deadline = convertDeadline(request.getDeadline());

        Set<ApplicationUser> receivers = new HashSet<>();

        if (!teamService.loadTeamById(request.getTeamId()).isPresent() && request.getTeamId() != 0) {
            throw new TeamNotFoundException("Could not find team with ID " + request.getTeamId());
        } else {
            if (request.getTeamId() != 0) {
                team = teamService.loadTeamById(request.getTeamId()).get();
                if (request.getReceiversIds().isEmpty()) {
                    receivers.addAll(team.getTeamMembers());
                } else {
                    for (Long id: request.getReceiversIds()) {
                        if (team.getTeamMembers().contains(applicationUserService.loadApplicationUserById(id).get())) {
                            receivers.add(applicationUserService.loadApplicationUserById(id).get());
                        }
                    }
                }

            } else {
                if (!request.getReceiversIds().isEmpty()) {
                    List<ApplicationUser> users = applicationUserService.getUsersByIds(request.getReceiversIds());

                    for (ApplicationUser user : users) {
                        if (!applicationUserService.loadApplicationUserById(user.getId()).get().getEnabled()) {
                            throw new UserNotFoundException("User with ID " + user.getId() + " is not active");
                        }
                    }

                    receivers.addAll(applicationUserService.getUsersByIds(request.getReceiversIds()));
                    team = null;
                } else {
                    throw new UserNotFoundException("Task must have receivers");
                }
            }
        }

        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                deadline,
                TaskPriority.parse(request.getPriority()),
                giver,
                receivers.stream().toList(),
                team
        );

        return taskRepository.save(task);
    }

//        TODO jak robimy tego typu rzeczy, czy nie lepiej jest przerzuć sprawadzanie i wyrzucanie wyjątków w load ?
    public void checkingConnectionBetweenTaskAndUser(Long taskId, Long userId) throws UserNotFoundException, TaskNotFoundException {
        if (!applicationUserService.loadApplicationUserById(userId).isPresent()) {
            throw new UserNotFoundException("Could not find user with ID " + userId);
        }
        if (!loadTaskById(taskId).isPresent()) {
            throw new TaskNotFoundException("Could not find task with ID " + userId);
        }
    }

    public Task tickTask(Long taskId, Long userId) throws UserNotFoundException, TaskNotFoundException {
        checkingConnectionBetweenTaskAndUser(taskId, userId);
        Task task = loadTaskById(taskId).get();
        ApplicationUser user = applicationUserService.loadApplicationUserById(userId).get();

        if (task.getReceiversWhoDone().contains(user)) {
            if (task.getIsDone()){
                task.setIsDone(false);
            }
            task.getReceiversWhoDone().remove(user);

        } else {
            List<ApplicationUser> whoDone = task.getReceiversWhoDone();
            whoDone.add(user);
            task.setReceiversWhoDone(whoDone);
        }

        if (task.getReceivers().size() == task.getReceiversWhoDone().size()) {
            task.setIsDone(true);
        }

        return taskRepository.save(task);
    }
}
