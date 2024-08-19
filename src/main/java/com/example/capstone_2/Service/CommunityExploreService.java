package com.example.capstone_2.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capstone_2.API.ApiException;
import com.example.capstone_2.Model.Comment;
import com.example.capstone_2.Model.CommunityExplore;
import com.example.capstone_2.Model.CommunityProfile;
import com.example.capstone_2.Model.Likes;
import com.example.capstone_2.Model.Project;
import com.example.capstone_2.Model.Rating;
import com.example.capstone_2.Model.Resource;
import com.example.capstone_2.Model.User;
import com.example.capstone_2.Repository.CommunityExploreRepository;
import com.example.capstone_2.Repository.CommunityProfileRepository;
import com.example.capstone_2.Repository.ResourceRepository;
import com.example.capstone_2.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityExploreService {
    
    private final CommunityExploreRepository communityExploreRepository; 
    private final UserRepository userRepository; 
    private final CommunityProfileRepository communityProfileRepository;
    private final ResourceRepository resourceRepository;

    private CommunityExplore communityExplore; 

    public CommunityExplore getDefaultCommunityExplore(){
        if(communityExplore == null){
            communityExplore = communityExploreRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Default CommunityExolore not found"));
        }
        return  communityExplore;
    }

    public List<CommunityExplore> getAllProducts(){
        return communityExploreRepository.findAll(); 
    }

    public CommunityExplore getExploreCommunityById(Integer id){
        return communityExploreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Explore Community not found")); 
    }


    public CommunityExplore addExploreCommunity(CommunityExplore communityExplore){
        return communityExploreRepository.save(communityExplore); 
    }

    public CommunityExplore updateExploreCommunity(Integer id, CommunityExplore communityExploreDetails){
        if(communityExploreRepository.existsById(id)){
            communityExploreDetails.setId(id); 
            return communityExploreRepository.save(communityExploreDetails); 
        }

        throw  new RuntimeException("Explore Community not found"); 
    }


    public void deleteExploreCommunity(Integer id){
        if(communityExploreRepository.existsById(id)){
            communityExploreRepository.deleteById(id);
        }else {
            throw new RuntimeException("Explore Community not found"); 
        }
    }


    public List<Project> getProjectByType(Project.Type type) {
        List<CommunityProfile> profiles = communityProfileRepository.findAll();
        List<Project> projects = new ArrayList<>();

        for (CommunityProfile profile : profiles) {
            for (Resource resource : profile.getResources()) {
                Project project = resource.getProject();
                if (project.getType().equals(type) && project.getStatus() == Project.Status.PUBLISHED) {
                    projects.add(project);
                }
            }
        }

        if (projects.isEmpty()) {
            throw new ApiException("Not found projects for the given type: " + type);
        }
        return projects;
    }





    // raintg
    public void addRating(Integer resourceId, Integer userId, int ratingValue){
        Resource resource = resourceRepository.findResourcesById(resourceId); 
        if(resource == null){
            throw new ApiException("not found Resource ID"); 
        }

        User user = userRepository.findUserById(userId); 
        if(user == null){
            throw new ApiException("not found user ID"); 
        }

        Rating rating = new Rating();
        rating.setProject(resource.getProject()); 
        rating.setUser(user);
        rating.setRating(ratingValue);
        resource.getProject().getRatings().add(rating);  
        resourceRepository.save(resource);
    }
    
    
    // Comment
    public void addComment(Integer resourceId, Integer userId, String content){
        Resource resource = resourceRepository.findResourcesById(resourceId); 
        if(resource == null){
            throw new ApiException("Not found resource with this ID: " + resourceId); 
        }

        User user = userRepository.findUserById(userId); 
        if(user == null){
            throw new ApiException("User with this ID: " + userId + ", Not found"); 
        }

        Comment comment = new Comment();
        comment.setProject(resource.getProject());
        comment.setUser(user);
        comment.setComment(content);
        resource.getProject().getComments().add(comment); 
        resourceRepository.save(resource); 
    }


    // Like
    public void addLike(Integer resourceId, Integer userId, boolean likee){
        Resource resource = resourceRepository.findResourcesById(resourceId); 
        if(resource == null){
            throw new ApiException("Not found resource with this ID: " + resourceId); 
        }

        User user = userRepository.findUserById(userId); 
        if(user == null){
            throw new ApiException("User with this ID: " + userId + ", Not found"); 
        }

        Likes likes = new Likes();
        likes.setProject(resource.getProject());
        likes.setUser(user);
        likes.setLiked(likee);
        resource.getProject().setLikesCount(resource.getProject().getLikesCount() + 1);
        resource.getProject().getLikes().add(likes); 
        resourceRepository.save(resource); 
    }

}
