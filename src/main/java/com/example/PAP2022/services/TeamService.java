package com.example.PAP2022.services;

import com.example.PAP2022.exceptions.TeamNotFoundException;
import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.payload.TeamMemberRequest;
import com.example.PAP2022.repository.ApplicationUserRepository;
import com.example.PAP2022.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.PAP2022.models.Team;
import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.Task;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private TeamRepository teamRepository;
    private ApplicationUserDetailsServiceImplementation applicationUserService;
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, ApplicationUserDetailsServiceImplementation applicationUserService, ApplicationUserRepository applicationUserRepository) {
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