package com.pap.crm_project.team;

import com.pap.crm_project.applicationuser.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public List<Team> getTeamsTeamLeader(ApplicationUser teamLeader){
        return teamRepository.findTeamByTeamLeader(teamLeader);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    public boolean saveTeam(Team team){
        if (teamRepository.findTeamByName(team.getName()).isPresent()) {
            return true;
        } else {
            teamRepository.save(team);
            return false;
        }
    }
}
