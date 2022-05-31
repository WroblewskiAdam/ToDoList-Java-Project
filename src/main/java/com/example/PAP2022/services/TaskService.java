package com.example.PAP2022.services;

import com.example.PAP2022.enums.TaskPriority;
import com.example.PAP2022.exceptions.*;
import com.example.PAP2022.models.Task;
import com.example.PAP2022.models.Team;
import com.example.PAP2022.payload.TaskRequest;
import com.example.PAP2022.repository.TaskRepository;
import com.example.PAP2022.models.ApplicationUser;

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

    public Task getTask(Long taskId) throws TaskNotFoundException {
        if (loadTaskById(taskId).isPresent()) {
            return loadTaskById(taskId).get();
        } else {
            throw new TaskNotFoundException("Could not find task with ID " + taskId);
        }
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll().stream()
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    public List<ApplicationUser> getTaskReceivers(Long taskId) throws TaskNotFoundException {
        return getTask(taskId).getReceivers();
    }

    public List<ApplicationUser> getTaskReceiversWhoDone(Long taskId) throws TaskNotFoundException {
        return getTask(taskId).getReceiversWhoDone();
    }


    // ############### User Only filters ##################

    public List<Task> getReceivedTasks(Long userId) throws UserNotFoundException {
        ApplicationUser user = applicationUserService.getApplicationUser(userId);
        return user.getTasks().stream()
                .filter(task -> !task.getReceiversWhoDone().contains(user))
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    public List<Task> getGivenTasks(Long userId) throws UserNotFoundException {
        ApplicationUser user = applicationUserService.getApplicationUser(userId);
        return taskRepository.findByGiver(user).stream()
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    public List<Task> getTodayTasks(Long userId) throws UserNotFoundException {
        return getReceivedTasks(userId).stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(1)))
                .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<Task> getTodayTasksGiven(Long userId) throws UserNotFoundException {
        return getGivenTasks(userId).stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(1)))
                .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<Task> getSevenDaysTasks(Long userId) throws UserNotFoundException {
        return getReceivedTasks(userId).stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(7)))
                .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<Task> getSevenDaysTasksGiven(Long userId) throws UserNotFoundException {
        return getGivenTasks(userId).stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(7)))
                .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<Task> getExpiredTasks(Long userId) throws UserNotFoundException {
        return getReceivedTasks(userId).stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<Task> getExpiredTasksGiven(Long userId) throws UserNotFoundException {
        return getGivenTasks(userId).stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<Task> getDoneGivenTasks(Long userId) throws UserNotFoundException {
        ApplicationUser user = applicationUserService.getApplicationUser(userId);
        return user.getTasks().stream()
                .filter(task -> task.getGiver() == user)
                .filter(Task::getIsDone)
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    // from the point of view of the user to whom the task has been assigned
    public List<Task> getDoneReceivedTasks(Long userId) throws UserNotFoundException {
        ApplicationUser user = applicationUserService.getApplicationUser(userId);
        return applicationUserService.getApplicationUser(userId).getTasks().stream()
                .filter(task -> task.getReceiversWhoDone().contains(user))
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }


    // ############### Private filters ##################

    public List<Task> getPrivateTasks(Long userId) throws UserNotFoundException {
        List<Task> tasks = getReceivedTasks(userId);

        List<Task> sortedTasks = tasks.stream()
                .filter(task -> task.getTeam() == null)
                .collect(Collectors.toList());
        return sortedTasks;
    }

    public List<Task> getPrivateTasksGiven(Long userId) throws UserNotFoundException {
        return getGivenTasks(userId).stream()
                .filter(task -> task.getTeam() == null)
                .collect(Collectors.toList());
    }

    public List<Task> getPrivateTodayTasks(Long userId) throws UserNotFoundException {
        return getTodayTasks(userId).stream()
                .filter(task -> task.getTeam() == null)
                .collect(Collectors.toList());
    }

    public List<Task> getPrivateTodayTasksGiven(Long userId) throws UserNotFoundException {
        return getTodayTasksGiven(userId).stream()
                .filter(task -> task.getTeam() == null)
                .collect(Collectors.toList());
    }

    public List<Task> getPrivateSevenDaysTasks(Long userId) throws UserNotFoundException {
        return getSevenDaysTasks(userId).stream()
                .filter(task -> task.getTeam() == null)
                .collect(Collectors.toList());
    }

    public List<Task> getPrivateSevenDaysTasksGiven(Long userId) throws UserNotFoundException {
        return getSevenDaysTasksGiven(userId).stream()
                .filter(task -> task.getTeam() == null)
                .collect(Collectors.toList());
    }

    public List<Task> getPrivateExpiredTasks(Long userId) throws UserNotFoundException {
        return getExpiredTasks(userId).stream()
                .filter(task -> task.getTeam() == null)
                .collect(Collectors.toList());
    }

    public List<Task> getPrivateExpiredTasksGiven(Long userId) throws UserNotFoundException {
        return getExpiredTasksGiven(userId).stream()
                .filter(task -> task.getTeam() == null)
                .collect(Collectors.toList());
    }

    public List<Task> getPrivateDoneGivenTasks(Long userId) throws UserNotFoundException {
        return getDoneGivenTasks(userId).stream()
                .filter(task -> task.getTeam() == null)
                .collect(Collectors.toList());
    }

    // from the point of view of the user to whom the task has been assigned
    public List<Task> getPrivateDoneReceivedTasks(Long userId) throws UserNotFoundException {
        return getDoneReceivedTasks(userId).stream()
                .filter(task -> task.getTeam() == null)
                .collect(Collectors.toList());
    }


    // ############### Team filters ##################

    public List<Task> getAllTasksTeam(Long teamId) throws TeamNotFoundException {
        return teamService.getTeam(teamId).getTeamTasks().stream()
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    public List<Task> getAllExpiredTasksTeam(Long teamId) throws TeamNotFoundException {
        return teamService.getTeam(teamId).getTeamTasks().stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    public List<Task> getAllDoneTasksTeam(Long teamId) throws TeamNotFoundException {
        return teamService.getTeam(teamId).getTeamTasks().stream()
                .filter(Task::getIsDone)
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    public List<Task> getAllReceivedTasksTeam(Long teamId, Long userId) throws TeamNotFoundException, UserNotFoundException {
        Team team = teamService.getTeam(teamId);
        ApplicationUser user = applicationUserService.getApplicationUser(userId);
        return getReceivedTasks(userId).stream()
                .filter(task -> task.getReceivers().contains(user))
                .filter(task -> Objects.equals(task.getTeam(), team))
                .collect(Collectors.toList());
    }

    public List<Task> getReceivedTasksTeam(Long teamId, Long userId) throws TeamNotFoundException, UserNotFoundException {
        Team team = teamService.getTeam(teamId);
        return getReceivedTasks(userId).stream()
                .filter(task -> Objects.equals(task.getTeam(), team))
                .collect(Collectors.toList());
    }

    public List<Task> getGivenTasksTeam(Long teamId, Long userId) throws TeamNotFoundException, UserNotFoundException {
        Team team = teamService.getTeam(teamId);
        return getGivenTasks(userId).stream()
                .filter(task -> Objects.equals(task.getTeam(), team))
                .collect(Collectors.toList());
    }

    public List<Task> getExpiredReceivedTasksTeam(Long teamId, Long userId) throws TeamNotFoundException, UserNotFoundException {
        Team team = teamService.getTeam(teamId);
        return getExpiredTasks(userId).stream()
                .filter(task -> Objects.equals(task.getTeam(), team))
                .collect(Collectors.toList());
    }

    public List<Task> getExpiredGivenTasksTeam(Long teamId, Long userId) throws TeamNotFoundException, UserNotFoundException {
        Team team = teamService.getTeam(teamId);
        return getExpiredTasksGiven(userId).stream()
                .filter(task -> Objects.equals(task.getTeam(), team))
                .collect(Collectors.toList());
    }

    public List<Task> getDoneReceivedTasksTeam(Long teamId, Long userId) throws TeamNotFoundException, UserNotFoundException {
        Team team = teamService.getTeam(teamId);
        return getDoneReceivedTasks(userId).stream()
                .filter(task -> Objects.equals(task.getTeam(), team))
                .collect(Collectors.toList());
    }

    public List<Task> getDoneGivenTasksTeam(Long teamId, Long userId) throws TeamNotFoundException, UserNotFoundException {
        Team team = teamService.getTeam(teamId);
        return getDoneGivenTasks(userId).stream()
                .filter(task -> Objects.equals(task.getTeam(), team))
                .collect(Collectors.toList());
    }

    public List<Task> getTodayTasksTeam(Long teamId) throws TeamNotFoundException {
        return teamService.getTeam(teamId).getTeamTasks().stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(1)))
                .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    public List<Task> getTodayTasksGivenTeam(Long teamId, Long userId) throws TeamNotFoundException, UserNotFoundException {
        Team team = teamService.getTeam(teamId);
        return getTodayTasksGiven(userId).stream()
                .filter(task -> Objects.equals(task.getTeam(), team))
                .collect(Collectors.toList());
    }

    public List<Task> getTodayTasksReceivedTeam(Long teamId, Long userId) throws TeamNotFoundException, UserNotFoundException {
        Team team = teamService.getTeam(teamId);
        return getTodayTasks(userId).stream()
                .filter(task -> Objects.equals(task.getTeam(), team))
                .collect(Collectors.toList());
    }

    public List<Task> getSevenDaysTasksTeam(Long teamId) throws TeamNotFoundException {
        return teamService.getTeam(teamId).getTeamTasks().stream()
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now().plusDays(7)))
                .filter(task -> task.getDeadline().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    public List<Task> getSevenDaysTasksGivenTeam(Long teamId, Long userId) throws TeamNotFoundException, UserNotFoundException {
        Team team = teamService.getTeam(teamId);
        return getSevenDaysTasksGiven(userId).stream()
                .filter(task -> Objects.equals(task.getTeam(), team))
                .collect(Collectors.toList());
    }

    public List<Task> getSevenDaysReceivedTasksTeam(Long teamId, Long userId) throws TeamNotFoundException, UserNotFoundException {
        Team team = teamService.getTeam(teamId);
        return getSevenDaysTasks(userId).stream()
                .filter(task -> Objects.equals(task.getTeam(), team))
                .collect(Collectors.toList());
    }


//    ################## Operation on task ##################

    public Long deleteTask(Long taskId) throws TeamNotFoundException {
        if (loadTaskById(taskId).isPresent()) {
                taskRepository.deleteById(taskId);
            } else {
                throw new TeamNotFoundException("Could not find task with ID " + taskId);
            }
        return taskId;
    }

    public LocalDateTime convertDeadline(String deadlineString) throws InvalidDeadlineDateException {
        LocalDateTime deadline;

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
            InvalidDeadlineDateException,
            InvalidTaskPriorityException {

        Task task = getTask(taskId);
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDeadline(convertDeadline(taskRequest.getDeadline()));
        task.setPriority(TaskPriority.parse(taskRequest.getPriority()));
        task.setReceivers(applicationUserService.getUsersByIds(taskRequest.getReceiversIds()));

        return taskRepository.save(task);
    }

    public Task saveTask(TaskRequest request) throws
            UserNotFoundException,
            TeamNotFoundException,
            InvalidDeadlineDateException, InvalidTaskPriorityException {

        ApplicationUser giver = applicationUserService.getApplicationUser(request.getGiverId());
        Team team;

        LocalDateTime deadline = convertDeadline(request.getDeadline());

        Set<ApplicationUser> receivers = new HashSet<>();

        if (request.getTeamId() == 0) {
            if (!request.getReceiversIds().isEmpty()) {
                List<ApplicationUser> users = applicationUserService.getUsersByIds(request.getReceiversIds());

                for (ApplicationUser user : users) {
                    if (!applicationUserService.getApplicationUser(user.getId()).getEnabled()) {
                        throw new UserNotFoundException("User with ID " + user.getId() + " is not active");
                    }
                }

                receivers.addAll(applicationUserService.getUsersByIds(request.getReceiversIds()));
                team = null;

            } else {
                throw new UserNotFoundException("Task must have receivers");
            }

        } else {
            team = teamService.getTeam(request.getTeamId());

            if (request.getReceiversIds().isEmpty()) {
                receivers.addAll(team.getTeamMembers());

            } else {
                for (Long id: request.getReceiversIds()) {
                    ApplicationUser user = applicationUserService.getApplicationUser(id);

                    if (team.getTeamMembers().contains(user)) {
                        receivers.add(user);
                    } else {
                        throw new UserNotFoundException(
                                "User with ID " + user.getId() + " is not in team " + team.getName());
                    }
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

    public Task tickTask(Long taskId, Long userId) throws UserNotFoundException, TaskNotFoundException {
        Task task = getTask(taskId);
        ApplicationUser user = applicationUserService.getApplicationUser(userId);

        if (!task.getReceivers().contains(user)) {
            throw new TaskNotFoundException(
                    "Task with Id " + taskId + " is not assigned to user with Id " + userId);
        }

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

    public Boolean checkIfTaskIsDoneByUser(Long taskId, Long userId) throws TaskNotFoundException, UserIsNotAssignedToTaskException {
        ApplicationUser user = applicationUserService.loadApplicationUserById(userId).get();
        if (!(getTaskReceivers(taskId).contains(user))) {
            throw new UserIsNotAssignedToTaskException(
                    "User with Id  " + userId + " is not assigned to task with Id " + taskId);
        }
        return (taskRepository.findById(taskId).get().getReceiversWhoDone().contains(user));
    }

}
