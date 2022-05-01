package com.example.PAP2022.controllers;

import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.Team;
import com.example.PAP2022.payload.TeamMemberRequest;
import com.example.PAP2022.payload.TeamRequest;
import com.example.PAP2022.services.ApplicationUserDetailsServiceImplementation;
import com.example.PAP2022.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;
    private final ApplicationUserDetailsServiceImplementation applicationUserService;

    @GetMapping("/all")
    public ResponseEntity<List<Team>> getAllTeams(){
        return ResponseEntity.ok().body(teamService.getAllTeams());
    }

    @GetMapping("/getMembers")
    public ResponseEntity<List<ApplicationUser>> getTeamMembers(@RequestParam Long teamId){
        return ResponseEntity.ok().body(teamService.getTeamById(teamId).get().getTeamMembers());
    }

    @GetMapping("/getTeamLeader")
    public ResponseEntity<ApplicationUser> getTeamLeader(@RequestParam Long teamId){
        return ResponseEntity.ok().body(teamService.getTeamById(teamId).get().getTeamLeader());
    }

    @GetMapping("/get")
    public ResponseEntity<?> getTeamById(@RequestParam Long teamId){
        return ResponseEntity.ok().body(teamService.getTeamById(teamId));
    }

    // TODO zabezpieczyć przed dodaniem teamu o takiej samej nazwie - czy potrzebne?
    @PostMapping("/save")
    public ResponseEntity<Team> saveTeam(@RequestBody TeamRequest request){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/teams/save").toUriString());
        ApplicationUser leader = applicationUserService.loadApplicationUserById(request.getTeamLeaderId()).get();
        List<ApplicationUser> teamMembers = applicationUserService.getListUsersByIds(request.getMembersIds());
        teamMembers.add(leader);

        return ResponseEntity.created(uri).body(teamService.saveTeam(new Team(
                request.getName(),
                leader,
                teamMembers
        )));
    }

    @PutMapping("/addMember")
    public ResponseEntity<Team> addMember(@RequestBody TeamMemberRequest teamMemberRequest){
        return ResponseEntity.ok().body(teamService.addMember(teamMemberRequest.getTeamId(), teamMemberRequest.getMemberId()));
    }

    //TODO zaimlementować metodę jaka na wejściu otrzymuje listę użytkowników i dodaje ich do teamu

    @PutMapping("/addMembers")
    public ResponseEntity<Team> addMembers(@RequestBody TeamMemberRequest teamMemberRequest){
        return ResponseEntity.ok().body(teamService.addMember(teamMemberRequest.getTeamId(), teamMemberRequest.getMemberId()));
    }

    @DeleteMapping("/deleteMember")
    public ResponseEntity<Team> deleteMember(@RequestBody TeamMemberRequest teamMemberRequest){
        return ResponseEntity.ok().body(teamService.deleteMember(teamMemberRequest.getTeamId(), teamMemberRequest.getMemberId()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteTeamById(@RequestParam Long teamId){
        try{
            return ResponseEntity.ok(teamService.deleteTeamById(teamId));
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
