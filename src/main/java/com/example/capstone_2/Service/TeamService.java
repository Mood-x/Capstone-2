package com.example.capstone_2.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capstone_2.API.ApiException;
import com.example.capstone_2.Model.Team;
import com.example.capstone_2.Model.User;
import com.example.capstone_2.Repository.TeamRepository;
import com.example.capstone_2.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
    
    private final TeamRepository teamRepository;
    private final UserRepository userRepository; 

    public void createTeam(Integer userId, Team team){
        User user = userRepository.findUserById(userId);

        if(user == null){
            throw new ApiException("User with this ID: " + userId + ", Not found"); 
        }

        if(!user.getPlan().getType().equals(user.getPlan().getType().PROFESSIONAL)){
            throw new ApiException("Starter plan cannot create teams upgrade your plan to get more features"); 
        }
        

        teamRepository.save(team); 
        user.setTeam(team);
        userRepository.save(user); 
    }


    public List<Team> getAllTeam(){
        return teamRepository.findAll(); 
    }

    public Team getTeamById(Integer id){
        return teamRepository.findById(id).orElse(null); 
    }

    public void deleteTeam(Integer id){
        teamRepository.deleteById(id);
    }



    public void addUserToTeam(Integer teamId, Integer userId){
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new ApiException("Team not found")); 

        User user = userRepository.findUserById(userId); 
        if(user == null){
            throw new ApiException("User not found"); 
        }

        user.setTeam(team);
        userRepository.save(user);


        team.getMembers().add(user); 
        teamRepository.save(team); 
    }

    public boolean hasPermission(Integer userId, Integer teamId){
        Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new ApiException("Team not found")); 

        User user = userRepository.findUserById(userId); 
        if(user == null){
            throw new ApiException("User not found"); 
        }

        return true; 
    }
}
