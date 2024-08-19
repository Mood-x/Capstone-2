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
import com.example.capstone_2.Model.Metrics;
import com.example.capstone_2.Service.MetricsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final MetricsService metricsService; 

    
    @GetExchange
    public ResponseEntity getMetrics(){
        return ResponseEntity.status(200).body(metricsService.getMetrics()); 
    }

    @GetMapping("/{id}")
    public ResponseEntity getMetricsById(Integer id){
        Metrics metrics = metricsService.getMetricsById(id); 
        return ResponseEntity.status(200).body(metrics); 
    }

    @PostMapping("/add")
    public ResponseEntity addMetrics(@RequestBody Metrics metrics, Errors err){
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage(); 
            return ResponseEntity.status(400).body(new ApiResponse(message)); 
        }

        metricsService.AddMetrics(metrics);
        return ResponseEntity.status(200).body(new ApiResponse("Metrics added successfully")); 
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMetrics(Integer id, @RequestBody Metrics metrics, Errors err){
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage(); 
            return ResponseEntity.status(400).body(new ApiResponse(message)); 
        }

        metricsService.updateMetrics(id, metrics);
        return ResponseEntity.status(200).body(new ApiResponse("Metrics updated successfully")); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMetrics(Integer id){
        metricsService.deleteMetrics(id);
        return ResponseEntity.status(200).body(new ApiResponse("Metrics deleted successfully")); 
    }

}
