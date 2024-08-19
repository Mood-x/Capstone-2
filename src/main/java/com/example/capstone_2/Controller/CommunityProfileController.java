package com.example.capstone_2.Controller;

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
import com.example.capstone_2.Model.CommunityProfile;
import com.example.capstone_2.Model.User;
import com.example.capstone_2.Service.CommunityProfileService;
import com.example.capstone_2.Service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/community-profile")
@RequiredArgsConstructor
public class CommunityProfileController {

    private final CommunityProfileService communityProfileService;
    private final UserService userService; 

// ===============================================================================

    @GetMapping("/{userId}/communityProfile")
    public ResponseEntity getCommunityProfile(@PathVariable Integer userId) {
        CommunityProfile profile = communityProfileService.getCommunityProfile(userId);
        return ResponseEntity.status(200).body(profile);
    }

    @PutMapping("/{userId}/communityProfile")
    public ResponseEntity updateCommunityProfile(@PathVariable Integer userId, @RequestBody CommunityProfile profile) {
        communityProfileService.updateCommunityProfile(userId, profile);
        return ResponseEntity.status(200).body(new ApiResponse("Update Community Profile"));
    }

    // +5
    @PostMapping("/saved/save-project")
    public ResponseEntity saveProjectToCommunityProfile(@RequestParam Integer userId, @RequestParam Integer projectId){
        User user = userService.getUserById(userId); 
        communityProfileService.saveProjectToCommunityProfile(user, projectId);
        return ResponseEntity.status(200).body(new ApiResponse("Project saved succssfully")); 
    }

    // +6
    @DeleteMapping("/saved/unsave-project")
    public ResponseEntity removeSavedFromCommunityProfile(@RequestParam Integer userId, @RequestParam Integer projectId){
        User user = userService.getUserById(userId); 
        communityProfileService.removeProjectToCommunityProfile(user, projectId);
        return ResponseEntity.status(200).body(new ApiResponse("Project removed succssfully from saved list")); 
    }


    // +7
    @PostMapping("/follow/{followerId}/{followingId}")
    public ResponseEntity followUser(@PathVariable Integer followerId, @PathVariable Integer followingId){ 
        communityProfileService.followUser(followerId, followingId);
        return ResponseEntity.status(200).body(new ApiResponse("Follow")); 
    }

    // +8
    @PostMapping("/unFollow/{followerId}/{followingId}")
    public ResponseEntity unFollowUser(@PathVariable Integer followerId, @PathVariable Integer followingId){
        communityProfileService.unFollowUser(followerId, followingId);
        return ResponseEntity.status(200).body(new ApiResponse("Unfollow")); 
    }


    // +9
    @DeleteMapping("/resource/remove")
    public ResponseEntity removeResouceFromCoummunityProfile(@RequestParam Integer userId, @RequestParam Integer projectId){
        User user = userService.getUserById(userId); 
        communityProfileService.removeResouceFromCoummunityProfile(user, projectId);
        return ResponseEntity.status(200).body(new ApiResponse("Project removed succssfully from resource list")); 
    }
    
}
