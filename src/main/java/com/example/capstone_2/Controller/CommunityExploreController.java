package com.example.capstone_2.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.capstone_2.Model.CommunityExplore;
import com.example.capstone_2.Model.Project;
import com.example.capstone_2.Service.CommunityExploreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/Explore-Community")
@RequiredArgsConstructor
public class CommunityExploreController {

    private final CommunityExploreService communityExploreService; 

    @GetMapping
    public ResponseEntity getAllProducts(){
        List<CommunityExplore> communities = communityExploreService.getAllProducts(); 
        return ResponseEntity.status(200).body(communities); 
    }

    @GetMapping("/{id}")
    public ResponseEntity getExploreCommunityById(@PathVariable Integer id){
        try {
            CommunityExplore community = communityExploreService.getExploreCommunityById(id); 
            return ResponseEntity.ok(community); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
        }
    }

    @PostMapping
    public ResponseEntity addExploreCommunity(@RequestBody CommunityExplore communityExplore){
        CommunityExplore newCommunity = communityExploreService.addExploreCommunity(communityExplore); 
        return ResponseEntity.status(HttpStatus.CREATED).body(newCommunity); 
    }


    @PutMapping("/{id}")
    public ResponseEntity updateExploreCommunity(@PathVariable Integer id, @RequestBody CommunityExplore communityExplore){
        try {
            CommunityExplore updateCommunity = communityExploreService.updateExploreCommunity(id, communityExplore); 
            return ResponseEntity.ok(updateCommunity); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteExploreCommunity(@PathVariable Integer id){
        try {
            communityExploreService.deleteExploreCommunity(id); 
            return ResponseEntity.noContent().build(); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
        }
    }

    // +1
    @GetMapping("/projects")
    public List<Project> getProjectsByType(@RequestParam Project.Type type) {
        return communityExploreService.getProjectByType(type);
    }

    // +2
    @PostMapping("/{resourceId}/{userId}/rating")
    public ResponseEntity addRating(@PathVariable Integer resourceId, @PathVariable Integer userId, @RequestParam int ratingValue){
        communityExploreService.addRating(resourceId, userId, ratingValue);
        return ResponseEntity.status(200).body(new ApiResponse("Rating successfully")); 
    }

    // +3
    @PostMapping("/{resourceId}/{userId}/comment")
    public ResponseEntity addRating(@PathVariable Integer resourceId, @PathVariable Integer userId, @RequestBody String content){
        communityExploreService.addComment(resourceId, userId, content);
        return ResponseEntity.status(200).body(new ApiResponse("Comment added successfully")); 
    }

    // +4
    @PostMapping("/{resourceId}/{userId}/like")
    public ResponseEntity addLike(@PathVariable Integer resourceId, @PathVariable Integer userId, @RequestParam boolean isLike){
        communityExploreService.addLike(resourceId, userId, isLike);
        return ResponseEntity.status(200).body(new ApiResponse("Like added successfully")); 
    }
}
