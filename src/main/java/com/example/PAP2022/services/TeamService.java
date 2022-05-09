package com.example.PAP2022.services;

import com.example.PAP2022.exceptions.TeamNotFoundException;
import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.models.Task;
import com.example.PAP2022.payload.TeamMemberRequest;
import com.example.PAP2022.payload.TeamRequest;
import com.example.PAP2022.repository.ApplicationUserRepository;
import com.example.PAP2022.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PAP2022.models.Team;
import com.example.PAP2022.models.ApplicationUser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ApplicationUserDetailsService applicationUserService;
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository,
                       ApplicationUserDetailsService applicationUserService,
                       ApplicationUserRepository applicationUserRepository) {
        this.teamRepository = teamRepository;
        this.applicationUserService = applicationUserService;
        this.applicationUserRepository = applicationUserRepository;
    }

    public Optional<Team> loadTeamById(Long id) {
        return teamRepository.findById(id);
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public List<Task> getTeamTasks(Long teamId) {
        return teamRepository.getById(teamId).getTeamTasks();
    }

    public List<ApplicationUser> getTeamMembers(Long teamId) {
        return teamRepository.getById(teamId).getTeamMembers();
    }

    public ApplicationUser getTeamLeader(Long teamId) {
        return teamRepository.getById(teamId).getTeamLeader();
    }

    public Long deleteTeamById(Long id) {
        teamRepository.deleteById(id);
        return id;
    }

    public Team saveTeam(Team team){
        return teamRepository.save(team);
    }

    public Team addMember(ApplicationUser applicationUser, Team team) {
        team.addMemberToTeam(applicationUser);
        return teamRepository.save(team);
    }

    public Team addTask(Task task, Team team){
        team.addTaskToTeam(task);
        return teamRepository.save(team);
    }

    public Team editTeam(Long teamId, TeamRequest teamRequest) throws UserNotFoundException, TeamNotFoundException {
        if (!applicationUserService.loadApplicationUserById(teamRequest.getTeamLeaderId()).isPresent()) {
            throw new UserNotFoundException("Could not find user with ID " + teamRequest.getTeamLeaderId());
        }

        if (loadTeamById(teamId).isPresent()) {
            Team team = loadTeamById(teamId).get();

            team.setName(teamRequest.getName());
            team.setTeamLeader(applicationUserService.loadApplicationUserById(teamRequest.getTeamLeaderId()).get());

            return saveTeam(team);

        } else {
            throw new TeamNotFoundException("Could not find team with ID " + teamId);
        }
    }

    public Team deleteMember(Long teamId, Long memberId){
        Team team = teamRepository.getById(teamId);
        List<ApplicationUser> filteredMembers = team.getTeamMembers().stream()
                .filter(applicationUser -> applicationUser.getId() != memberId)
                .collect(Collectors.toList());
        team.setTeamMembers(filteredMembers);

        return teamRepository.save(team);
    }

    public void teamRequestValidation(TeamMemberRequest teamMemberRequest) throws TeamNotFoundException, UserNotFoundException {
        if (!loadTeamById(teamMemberRequest.getTeamId()).isPresent()) {
            throw new TeamNotFoundException("Could not find team with ID " + teamMemberRequest.getTeamId());
        }

        if (!applicationUserService.loadApplicationUserById(teamMemberRequest.getMemberId()).isPresent()) {
            throw new UserNotFoundException("Could not find user with ID " + teamMemberRequest.getMemberId());
        }
    }
}
