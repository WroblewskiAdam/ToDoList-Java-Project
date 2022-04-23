package com.example.PAP2022.controllers;

import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.Team;
import com.example.PAP2022.repository.ApplicationUserRepository;
import com.example.PAP2022.security.jwt.JwtUnit;
import com.example.PAP2022.services.ApplicationUserDetailsServiceImplementation;
import com.example.PAP2022.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/showTeamLeaders")
    public ResponseEntity<List<Team>> getTeamsTeamLeader(@RequestBody ApplicationUser applicationUser){
      return ResponseEntity.ok().body(teamService.getTeamsTeamLeader(applicationUser));
    }

    @GetMapping("/showMembers")
    public ResponseEntity<List<Team>> getTeamsMember(@RequestBody ApplicationUser applicationUser){
        return ResponseEntity.ok().body(applicationUserService.getApplicationUserById(applicationUser.getId()).get().getTeams());
    }

    @PostMapping("/save")
    public ResponseEntity<Team> saveTeam(@RequestBody Team team){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/teams/save").toUriString());
        return ResponseEntity.created(uri).body(teamService.saveTeam(team));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getTeamById(@RequestBody Long id){
        return ResponseEntity.ok().body(teamService.getTeamById(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteTeamById(@RequestBody Long id){
        try{
            return ResponseEntity.ok(teamService.deleteTeamById(id));
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
