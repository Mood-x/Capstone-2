package com.example.capstone_2.Controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.capstone_2.API.ApiResponse;
import com.example.capstone_2.Model.Project;
import com.example.capstone_2.Model.User;
import com.example.capstone_2.Service.ProjectService;
import com.example.capstone_2.Service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService; 


    
    @GetMapping("/{id}")
    public ResponseEntity getProjectById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(projectService.getProjectById(id)); 
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity getProjectsByUserId(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(projectService.getProjectsByUserId(userId)); 
    }

    @GetMapping("/status/{status}")
    public ResponseEntity getProjectsByStatus(@PathVariable Project.Status status){
        if(!status.equals("PUBLISHED") && !status.equals("DRAFT")){
            return ResponseEntity.status(400).body(new ApiResponse("Status must be either ['PUBLISHED' or 'DRAFT']")); 
        }
        return ResponseEntity.status(200).body(projectService.getProjectsByStatus(status)); 
    }



    // +10
    @GetMapping("/published/{startDate}/{endDate}")
    public ResponseEntity getProjectsByPublishedDate(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate){
        return ResponseEntity.status(200).body(projectService.getProjectsByPublishedDate(startDate, endDate));
    }


    @PostMapping("/add")
    public ResponseEntity addProject(@RequestParam Integer id, @Valid @RequestBody Project project, Errors err){
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage(); 
            return ResponseEntity.status(400).body(new ApiResponse(message)); 
        }


        projectService.addProject(id, project);
        return ResponseEntity.status(200).body(new ApiResponse("Project added successfuly")); 
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProject(@PathVariable Integer id, @Valid @RequestBody Project project, Errors err){
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage(); 
            return ResponseEntity.status(400).body(new ApiResponse(message)); 
        }

        projectService.updateProject(id, project);
        return ResponseEntity.status(200).body(new ApiResponse("Project updated successfuly")); 
    }

    @DeleteMapping("/{userId}/{projectId}")
    public ResponseEntity deleteProject(@PathVariable Integer userId, @PathVariable Integer projectId){
        projectService.deleteProject(userId, projectId);
        return ResponseEntity.status(200).body(new ApiResponse("Project deleted successfuly")); 
    }


    // +11
    @PostMapping("/{projectId}/publish")
    public ResponseEntity<String> publishProjectToCommunityProfile(@PathVariable Integer projectId, @RequestParam Integer userId) {
        User user = userService.getUserById(userId);
        projectService.publishProjectToCommunityProfile(user, projectId);
        return ResponseEntity.ok("Project published successfully!");
    }
}
