package com.example.PAP2022.controllers;

import com.example.PAP2022.payload.TeamMemberRequest;
import com.example.PAP2022.payload.TeamRequest;
import com.example.PAP2022.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/teams")
public class TeamController{

    private final TeamService teamService;

    @GetMapping("/get")
    public ResponseEntity<?> getTeam(@RequestParam Long teamId) {
        try {
            return ResponseEntity.ok().body(teamService.getTeam(teamId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTeams() { return ResponseEntity.ok().body(teamService.getAllTeams());}

    @GetMapping("/getMembers")
    public ResponseEntity<?> getTeamMembers(@RequestParam Long teamId) {
        try {
            return ResponseEntity.ok().body(teamService.getTeamMembers(teamId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTeamLeader")
    public ResponseEntity<?> getTeamLeader(@RequestParam Long teamId) {
        try {
            return ResponseEntity.ok().body(teamService.getTeamLeader(teamId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTeamTasks")
    public ResponseEntity<?> getTeamTasks(@RequestParam Long teamId) {
        try {
            return ResponseEntity.ok().body(teamService.getTeamTasks(teamId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTeamName")
    public ResponseEntity<?> getTeamName(@RequestParam Long teamId) {
        try {
            return ResponseEntity.ok().body(teamService.getTeamName(teamId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveTeam(@RequestBody TeamRequest request) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/teams/save").toUriString());
            return ResponseEntity.created(uri).body(teamService.saveTeam(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editTeam(@RequestParam Long teamId, @RequestBody TeamRequest teamRequest) {
        try {
            return ResponseEntity.ok().body(teamService.editTeam(teamId, teamRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/addMember")
    public ResponseEntity<?> addMember(@RequestBody TeamMemberRequest teamMemberRequest){
        try {
            return ResponseEntity.ok().body(teamService.addMember(
                    teamMemberRequest.getTeamId(),
                    teamMemberRequest.getMemberId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteMember")
    public ResponseEntity<?> deleteMember(@RequestBody TeamMemberRequest teamMemberRequest) {
        try {
            return ResponseEntity.ok().body(teamService.deleteMember(
                    teamMemberRequest.getTeamId(),
                    teamMemberRequest.getMemberId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTeam(@RequestParam Long teamId) {
        try {
            return ResponseEntity.ok().body(teamService.deleteTeam(teamId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
