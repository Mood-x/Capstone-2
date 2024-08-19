package com.example.capstone_2.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capstone_2.API.ApiException;
import com.example.capstone_2.Model.Saved;
import com.example.capstone_2.Repository.SavedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SavedService {

    private final SavedRepository savedRepository;

    public List<Saved> getSaved(){
        return savedRepository.findAll(); 
    }

    public Saved getSavedById(Integer id){
        Saved saved = savedRepository.findSavedById(id); 
        if(saved == null){
            throw new ApiException("Saved with this ID: " + id + ", Not found"); 
        }

        return saved; 
    }

    public void AddSaved(Saved saved){
        savedRepository.save(saved); 
    }

    public void updateSaved(Integer id, Saved updateSaved){
        Saved saved = savedRepository.findSavedById(id); 
        if(saved == null){
            throw new ApiException("Saved with this ID: " + id + ", Not found"); 
        }

        savedRepository.save(saved); 
    }

    public void deleteSaved(Integer id){
        Saved saved = savedRepository.findSavedById(id); 
        if(saved == null){
            throw new ApiException("Saved with this ID: " + id + ", Not found"); 
        }

        savedRepository.delete(saved);
    }
}
