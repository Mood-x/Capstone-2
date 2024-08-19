package com.example.capstone_2.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.capstone_2.API.ApiResponse;
import com.example.capstone_2.Model.Plan;
import com.example.capstone_2.Service.PlanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService; 

    @GetMapping
    public ResponseEntity getPlans(){
        return ResponseEntity.status(200).body(planService.getPlans()); 
    }

    @GetMapping("{id}")
    public ResponseEntity getPlanById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(planService.getPlanById(id)); 
    }

    @PostMapping("/add")
    public ResponseEntity addPlan(@RequestBody Plan plan, Errors err){
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage(); 
            return ResponseEntity.status(400).body(message); 
        }

        planService.addPlan(plan);
        return ResponseEntity.status(200).body(new ApiResponse("Plan added successfully")); 
    }
}
