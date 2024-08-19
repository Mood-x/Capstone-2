package com.example.capstone_2.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capstone_2.API.ApiException;
import com.example.capstone_2.Model.CommunityExplore;
import com.example.capstone_2.Model.CommunityProfile;
import com.example.capstone_2.Model.Plan;
import com.example.capstone_2.Model.Project;
import com.example.capstone_2.Model.Project.Status;
import com.example.capstone_2.Model.Resource;
import com.example.capstone_2.Model.User;
import com.example.capstone_2.Repository.CommunityExploreRepository;
import com.example.capstone_2.Repository.CommunityProfileRepository;
import com.example.capstone_2.Repository.ProjectRepository;
import com.example.capstone_2.Repository.ResourceRepository;
import com.example.capstone_2.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final CommunityProfileRepository communityProfileRepository;
    private final ResourceRepository resourceRerpository; 
    private final CommunityExploreRepository communityExploreRepository; 
    
    
    public List<Project> getProjectsByUserId(Integer userid){
        List<Project> projects = projectRepository.findProjectsByUserId(userid);
        if(projects.isEmpty()){
            throw new ApiException("Not found project with this ID "+ userid); 
        }

        return projects; 
    }

    public List<Project> getProjectsByStatus(Project.Status status){
        List<Project> projects = projectRepository.findProjectByStatus(status); 
        if(projects.isEmpty()){
            throw new ApiException("Not found projects with this status "+ status); 
        }

        return projects; 
    }


    public Project getProjectById(Integer id){
        Project project = projectRepository.findProjectById(id); 
        if(project == null){
            throw new ApiException("Not found project with this ID "+ id); 
        }

        return project;
    }


    // public List<Project> getProjectsByPublishedDate(LocalDateTime startDate, LocalDateTime endDate){
    //     List<Project> projects = projectRepository.findProjectByPublishedAtBetween(startDate, endDate); 
    //     if(projects.isEmpty()){
    //         throw new ApiException("Not found projects with Between "+ startDate + " and " + endDate); 
    //     }

    //     return projects; 
    // }


    public void addProject(Integer id, Project project){
        User user = userRepository.findUserById(id); 
        if(project == null){
            throw new ApiException("Not found project with this ID "+ id); 
        }


        if(!canUserAddMoreProjects(user)){
            throw new ApiException("Project limit reached for the current plan.");
        }
        project.setUser(user);
        projectRepository.save(project); 
    }
    

    public void updateProject(Integer id, Project updateProject){
        Project project = projectRepository.findProjectById(id); 
        if(project == null){
            throw new ApiException("Not found project with this ID "+ id); 
        }
        
        project.setTitle(updateProject.getTitle());
        project.setDescription(updateProject.getDescription());
        project.setPrice(updateProject.getPrice());
    }

    public void deleteProject(Integer userId, Integer id){
        Project project = projectRepository.findProjectById(id); 
        if(project == null){
            throw new ApiException("Not found project with this ID "+ id); 
        }

        projectRepository.delete(project);
    }

    public void publishProject(Integer id){
        Project project = projectRepository.findProjectById(id); 
        if(project == null){
            throw new ApiException("Not found project with this ID "+ id); 
        }
        project.setStatus(Status.PUBLISHED);
        project.setPublishedAt(LocalDateTime.now());
        projectRepository.save(project); 
    }


    public boolean canUserAddMoreProjects(User user){
        if(user.getPlan() == null){
            return true; 
        }

        int maxProject; 

        switch (user.getPlan().getType()) {
            case STARTER:
                maxProject = 1; 
                break;

            case PROFESSIONAL: 
                maxProject = Integer.MAX_VALUE; 
                break; 

            default:
                maxProject = 0; 
                break;
        }
        return user.getProjects().size() < maxProject; 
    }

    // ====================================================

    public void publishProjectToCommunityProfile(User user, Integer projectId){
        Project project = projectRepository.findProjectById(projectId); 
        if(project == null){
            throw new ApiException("Project not found"); 
        }
    
        if(project.getStatus() != Project.Status.DRAFT){
            throw new ApiException("Only projects in DRAFT status can be published"); 
        }

        if(!canPublishProject(user)){
            throw new ApiException("Users with Starter plan cannot publish projects."); 
        }

        project.setStatus(Project.Status.PUBLISHED);
        project.setPublishedAt(LocalDateTime.now());

        CommunityProfile profile = communityProfileRepository.findCommunityProfileByUser(user); 
        if(profile == null){
            throw new ApiException("Community profile not found for user: " + user.getUsername()); 
        }
        Resource resource = new Resource(); 
        resource.setCommunityProfile(profile);
        resource.setProject(project);
        resourceRerpository.save(resource);
        
        profile.getResources().add(resource); 
        communityProfileRepository.save(profile);

        CommunityExplore explore = profile.getCommunityExplore(); 
        if(explore != null){
            explore.getProjects().add(project); 
            communityExploreRepository.save(explore); 
        }
    }
    


    private boolean canPublishProject(User user){
        return user.getPlan() == null || user.getPlan().getType() != Plan.SubPlan.STARTER; 
    }
}
