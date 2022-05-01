package com.example.PAP2022.controllers;

import com.example.PAP2022.exceptions.TeamNotFoundException;
import com.example.PAP2022.exceptions.UserExistsException;
import com.example.PAP2022.exceptions.UserNotFoundException;
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

    @GetMapping("/get")
    public ResponseEntity<?> getTeamById(@RequestParam Long teamId) {
        if (teamService.loadTeamById(teamId).isPresent()) {
            return ResponseEntity.ok().body(teamService.loadTeamById(teamId));
        } else {
            return ResponseEntity.badRequest().body(new TeamNotFoundException("Could not find team with ID " + teamId).getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTeams() { return ResponseEntity.ok().body(teamService.getAllTeams());}

    @GetMapping("/getMembers")
    public ResponseEntity<?> getTeamMembers(@RequestParam Long teamId) {
        if (teamService.loadTeamById(teamId).isPresent()) {
            return ResponseEntity.ok().body(teamService.loadTeamById(teamId).get().getTeamMembers());
        } else {
            return ResponseEntity.badRequest().body(new TeamNotFoundException("Could not find team with ID " + teamId).getMessage());
        }
    }

    @GetMapping("/getTeamLeader")
    public ResponseEntity<?> getTeamLeader(@RequestParam Long teamId) {
        if (teamService.loadTeamById(teamId).isPresent()) {
            return ResponseEntity.ok().body(teamService.loadTeamById(teamId).get().getTeamLeader());
        } else {
            return ResponseEntity.badRequest().body(new TeamNotFoundException("Could not find team with ID " + teamId).getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveTeam(@RequestBody TeamRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/teams/save").toUriString());
        ApplicationUser leader = applicationUserService.loadApplicationUserById(request.getTeamLeaderId()).get();
        List<ApplicationUser> teamMembers = applicationUserService.getUsersByIds(request.getMembersIds());
//      TODO Jak ty widzisz zapisywanie zespołów, czy będziesz pokazywał userów bez teamleadera czy z teamleaderem?
//          bo to co jest poniżej to tylko tak na chwilę jest zrobione
        if (!teamMembers.contains(leader)) {
            teamMembers.add(leader);
        }

        return ResponseEntity.created(uri).body(teamService.saveTeam(new Team(
                request.getName(),
                leader,
                teamMembers
        )));
    }

    @PutMapping("/addMember")
    public ResponseEntity<?> addMember(@RequestBody TeamMemberRequest teamMemberRequest){
        try {
            teamService.teamRequestValidation(teamMemberRequest);
            Team team = teamService.loadTeamById(teamMemberRequest.getTeamId()).get();
            ApplicationUser applicationUser = applicationUserService.loadApplicationUserById(
                    teamMemberRequest.getMemberId()).get();

            if (team.getTeamMembers().contains(applicationUser)) {
                return ResponseEntity.badRequest().body(
                        new UserExistsException(
                                "User with ID " + teamMemberRequest.getMemberId() + " is already a member").getMessage());
            }

            return ResponseEntity.ok().body(teamService.addMember(applicationUser, team));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteMember")
    public ResponseEntity<?> deleteMember(@RequestBody TeamMemberRequest teamMemberRequest) {
        try {
            teamService.teamRequestValidation(teamMemberRequest);
            Team team = teamService.loadTeamById(teamMemberRequest.getTeamId()).get();
            ApplicationUser applicationUser = applicationUserService.loadApplicationUserById(
                    teamMemberRequest.getMemberId()).get();

            if (!team.getTeamMembers().contains(applicationUser)) {
                return ResponseEntity.badRequest().body(
                        new UserNotFoundException(
                                "User with ID " + teamMemberRequest.getMemberId() + " is not a member").getMessage());
            }

            return ResponseEntity.ok().body(teamService.deleteMember(
                            teamMemberRequest.getTeamId(), teamMemberRequest.getMemberId()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTeamById(@RequestParam Long teamId) {
        if (teamService.loadTeamById(teamId).isPresent()) {
            return ResponseEntity.ok(teamService.deleteTeamById(teamId));
        } else {
            return ResponseEntity.badRequest().body(
                    new TeamNotFoundException(
                            "Could not find team with ID " + teamId).getMessage());
        }
    }
}
