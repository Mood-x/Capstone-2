package com.example.capstone_2.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.capstone_2.API.ApiResponse;
import com.example.capstone_2.Model.Team;
import com.example.capstone_2.Service.TeamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService; 
        
    @PostMapping("/{userId}")
    public ResponseEntity createTeam(@PathVariable Integer userId, @RequestBody Team team) {
        teamService.createTeam(userId,team);
        return ResponseEntity.status(200).body(new ApiResponse("Created team successfuly"));
    }

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeam();
    }

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable Integer id) {
        return teamService.getTeamById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Integer id) {
        teamService.deleteTeam(id);
    }


    @PostMapping("/{teamId}/addUser")
    public ResponseEntity addUserToTeam(@PathVariable Integer teamId, @RequestParam Integer userId){
        teamService.addUserToTeam(teamId, userId);
        return ResponseEntity.status(200).body("User added to team with role: "); 
    }


    @GetMapping("{teamId}/checkPermission")
    public ResponseEntity checkPermission(@PathVariable Integer teamId, @RequestParam Integer userId){
        boolean hasPermission = teamService.hasPermission(userId, teamId); 

        if(hasPermission){
            return ResponseEntity.status(200).body("User has the required permission"); 
        }else {
            return ResponseEntity.status(400).body("User does not have the required permission"); 
        }
    }
}
