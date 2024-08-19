package com.example.capstone_2.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capstone_2.API.ApiException;
import com.example.capstone_2.Model.Resource;
import com.example.capstone_2.Repository.ResourceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourcesRerpository;

    public List<Resource> getResources(){
        return resourcesRerpository.findAll(); 
    }

    public Resource getResourcesById(Integer id){
        Resource resources = resourcesRerpository.findResourcesById(id); 
        if(resources == null){
            throw new ApiException("Resources with this ID: " + id + ", Not found"); 
        }

        return resources; 
    }

    public void AddResources(Resource resources){
        resourcesRerpository.save(resources); 
    }

    public void updateResources(Integer id, Resource updateSaved){
        Resource resources = resourcesRerpository.findResourcesById(id); 
        if(resources == null){
            throw new ApiException("Resources with this ID: " + id + ", Not found"); 
        }

        resourcesRerpository.save(resources); 
    }

    public void deleteResources(Integer id){
        Resource resources = resourcesRerpository.findResourcesById(id); 
        if(resources == null){
            throw new ApiException("Resources with this ID: " + id + ", Not found"); 
        }

        resourcesRerpository.delete(resources);
    }
}
