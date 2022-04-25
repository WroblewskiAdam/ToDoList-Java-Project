package com.example.PAP2022.services;

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

    private TeamRepository teamRepository;
    private ApplicationUserDetailsServiceImplementation applicationUserService;
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, ApplicationUserDetailsServiceImplementation applicationUserService, ApplicationUserRepository applicationUserRepository) {
        this.teamRepository = teamRepository;
        this.applicationUserService = applicationUserService;
        this.applicationUserRepository = applicationUserRepository;
    }

    public Optional<Team> getTeamByName(String name) {
        return teamRepository.findTeamByName(name);
    }

    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public Long deleteTeamById(Long id) {
        teamRepository.deleteById(id);
        return id;
    }

    public List<Team> getTeamsTeamLeader(ApplicationUser teamLeader){
        return teamRepository.findTeamByTeamLeader(teamLeader);
    }

    // TODO dodać automatyczne dodawanie teamleadera do members
    public Team saveTeam(Team team){
        return teamRepository.save(team);
    }

    public Team addMember(Long teamId, Long memberId){
//        if(teamRepository.findById(teamId).isPresent() && applicationUserRepository.findById(memberId).isPresent()){
////            Team team = teamRepository.getById(teamId);
////            ApplicationUser member = applicationUserService.getApplicationUserById(memberId);
////
////            team.addMemberToTeam(member);
////
////            return teamRepository.save(team);
////        }
////        else{
////            return null;
////        }
        Team team = teamRepository.getById(teamId);
        ApplicationUser member = applicationUserService.getApplicationUserById(memberId);

        team.addMemberToTeam(member);

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
}
