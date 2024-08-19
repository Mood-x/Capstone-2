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
import com.example.capstone_2.Model.Saved;
import com.example.capstone_2.Service.SavedService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/saved")
@RequiredArgsConstructor
public class SavedController {

    private final SavedService savedService;

    @GetExchange
    public ResponseEntity getSaved(){
        return ResponseEntity.status(200).body(savedService.getSaved()); 
    }

    @GetMapping("/{id}")
    public ResponseEntity getSavedById(Integer id){
        Saved saved = savedService.getSavedById(id); 
        return ResponseEntity.status(200).body(saved); 
    }

    @PostMapping("/add")
    public ResponseEntity addSaved(@RequestBody Saved saved, Errors err){
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage(); 
            return ResponseEntity.status(400).body(new ApiResponse(message)); 
        }

        savedService.AddSaved(saved);
        return ResponseEntity.status(200).body(new ApiResponse("Saved added successfully")); 
    }

    @PutMapping("/{id}")
    public ResponseEntity updateSaved(Integer id, @RequestBody Saved saved, Errors err){
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage(); 
            return ResponseEntity.status(400).body(new ApiResponse(message)); 
        }

        savedService.updateSaved(id, saved);
        return ResponseEntity.status(200).body(new ApiResponse("Saved updated successfully")); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSaved(Integer id){
        savedService.deleteSaved(id);
        return ResponseEntity.status(200).body(new ApiResponse("Saved deleted successfully")); 
    }

}
