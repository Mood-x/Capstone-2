package com.example.capstone_2.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.example.capstone_2.API.ApiResponse;
import com.example.capstone_2.Model.Resource;
import com.example.capstone_2.Service.ResourceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/Resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourcesService;

    @GetExchange
    public ResponseEntity getResources(){
        return ResponseEntity.status(200).body(resourcesService.getResources()); 
    }

    @GetMapping("/{id}")
    public ResponseEntity findResourcesById(Integer id){
        Resource resources = resourcesService.getResourcesById(id); 
        return ResponseEntity.status(200).body(resources); 
    }

    @PostMapping("/add")
    public ResponseEntity AddResources(@RequestBody Resource resources, Errors err){
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage(); 
            return ResponseEntity.status(400).body(new ApiResponse(message)); 
        }

        resourcesService.AddResources(resources);
        return ResponseEntity.status(200).body(new ApiResponse("Resources added successfully")); 
    }

    @PutMapping("/{id}")
    public ResponseEntity updateResources(Integer id, @RequestBody Resource resources, Errors err){
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage(); 
            return ResponseEntity.status(400).body(new ApiResponse(message)); 
        }

        resourcesService.updateResources(id, resources);
        return ResponseEntity.status(200).body(new ApiResponse("Resources updated successfully")); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteResources(Integer id){
        resourcesService.deleteResources(id);
        return ResponseEntity.status(200).body(new ApiResponse("Resources deleted successfully")); 
    }

}
