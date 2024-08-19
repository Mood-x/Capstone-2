package com.example.capstone_2.Service;

import org.springframework.stereotype.Service;

import com.example.capstone_2.API.ApiException;
import com.example.capstone_2.Model.CommunityProfile;
import com.example.capstone_2.Model.Project;
import com.example.capstone_2.Model.Saved;
import com.example.capstone_2.Model.User;
import com.example.capstone_2.Repository.CommunityProfileRepository;
import com.example.capstone_2.Repository.ProjectRepository;
import com.example.capstone_2.Repository.SavedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityProfileService {

    private final CommunityProfileRepository communityProfileRepository;
    private final ProjectRepository projectRepository; 
    private final SavedRepository savedRepository; 


// ========================= [ GET COMMUNITY PROFILE ] ==========================
    public CommunityProfile getCommunityProfile(Integer userId) {
        CommunityProfile communityProfile = communityProfileRepository.findCommunityProfileByUserId(userId); 
        if(communityProfile == null){
            throw new ApiException("Community profile not found"); 
        }
        return communityProfile; 
    }


// ========================= [ UPDATE COMMUNITY PROFILE ] ==========================
    public void updateCommunityProfile(Integer userId, CommunityProfile profile) {
        CommunityProfile communityProfile = communityProfileRepository.findCommunityProfileByUserId(userId); 
        if(communityProfile == null){
            throw new ApiException("Community profile not found"); 
        }
        communityProfileRepository.save(communityProfile);
    }



// ========================= [ REMOVE RESOURCE FROM COMMUNITY PROFILE ] ==========================
    public void removeResourceFromProfile(Integer userId, Integer resourceId) {
        CommunityProfile communityProfile = communityProfileRepository.findCommunityProfileByUserId(userId); 
        if(communityProfile == null){
            throw new ApiException("Community profile not found"); 
        }

        communityProfile.getResources().remove(resourceId);
        communityProfileRepository.save(communityProfile);
    }



// ========================= [ SAVE PROJECT TO SAVED COMMUNITY PROFILE ] ==========================
    public void saveProjectToCommunityProfile(User user, Integer projectId){
        Project project = projectRepository.findProjectById(projectId);
        if(project == null ){
            throw new ApiException("Project not found"); 
        }

        CommunityProfile profile = communityProfileRepository.findCommunityProfileByUser(user); 
        if(profile == null){
            throw new ApiException("Community Profile not found for user: " + user.getUsername()); 
        }

        if(savedRepository.existsByProjectIdAndCommunityProfileId(projectId, profile.getId())){
            throw new ApiException("Project is already saved in your saved list"); 
        }

        Saved saved = new Saved(); 
        saved.setCommunityProfile(profile);
        saved.setProject(project);
        profile.getSaved().add(saved); 

        savedRepository.save(saved); 
        profile.getSaved().add(saved); 
        communityProfileRepository.save(profile); 
        
    }

// ========================= [ REMOVE PROJECT FROM SAVED COMMUNITY PROFILE ] ==========================
    public void removeProjectToCommunityProfile(User user, Integer projectId){
        Project project = projectRepository.findProjectById(projectId);
        if(project == null ){
            throw new ApiException("Project not found"); 
        }

        CommunityProfile profile = communityProfileRepository.findCommunityProfileByUser(user); 
        if(profile == null){
            throw new ApiException("Community Profile not found for user: " + user.getUsername()); 
        }

        if(profile.getSaved() != null){
            profile.getSaved().removeIf(p -> p.getId().equals(projectId));
            communityProfileRepository.save(profile); 
        }else {
            throw new ApiException("No project saved"); 
        }
    }



// ========================= [ FOLLOW COMMUNITY PROFILE ] ==========================
    public void followUser(Integer followerId, Integer followingId){
        CommunityProfile follower = communityProfileRepository.findCommunityProfileByUserId(followerId); 
        CommunityProfile following = communityProfileRepository.findCommunityProfileByUserId(followingId); 

        if(follower == null || following == null){
            throw  new ApiException(follower == null 
            ? "Follower with this ID: "+ followerId + ", Not found"
            :"Following with this ID: "+ followingId + ", Not found"); 
        }

        follower.getFollowing().add(followingId);
        communityProfileRepository.save(follower); 

        following.getFollowers().add(followerId); 
        communityProfileRepository.save(following);
    }

// ========================= [ UNFOLLOW COMMUNITY PROFILE ] ==========================
    public void unFollowUser(Integer followerId, Integer followingId){
        CommunityProfile follower = communityProfileRepository.findCommunityProfileByUserId(followerId); 
        CommunityProfile following = communityProfileRepository.findCommunityProfileByUserId(followingId); 

        if(follower == null || following == null){
            throw  new ApiException(follower == null 
            ? "Follower with this ID: "+ followerId + ", Not found"
            : "Following with this ID: "+ followingId + ", Not found"); 
        }

        follower.getFollowing().remove(followingId); 
        communityProfileRepository.save(follower);

        following.getFollowers().remove(followerId); 
        communityProfileRepository.save(following);
    }


// ========================= [ REMOVE RESOUCE FROM COMMUNITY PROFILE ] ==========================

    public void removeResouceFromCoummunityProfile(User user, Integer projectId){
        Project project = projectRepository.findProjectById(projectId);
        if(project == null ){
            throw new ApiException("Project not found"); 
        }

        CommunityProfile profile = communityProfileRepository.findCommunityProfileByUser(user); 
        if(profile == null){
            throw new ApiException("Community Profile not found for user: " + user.getUsername()); 
        }

        if(profile.getResources() != null){
            profile.getResources().removeIf(p -> p.getId().equals(projectId));
            communityProfileRepository.save(profile); 
        }else {
            throw new ApiException("Not found resource"); 
        }
    }
}
