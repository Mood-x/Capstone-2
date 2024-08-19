package com.example.capstone_2.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capstone_2.API.ApiException;
import com.example.capstone_2.Model.Metrics;
import com.example.capstone_2.Repository.MetricsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetricsService {

    private final MetricsRepository metricsRepository; 



    public List<Metrics> getMetrics(){
        return metricsRepository.findAll(); 
    }

    public Metrics getMetricsById(Integer id){
        Metrics metrics = metricsRepository.findMetricsById(id); 
        if(metrics == null){
            throw new ApiException("Metrics with this ID: " + id + ", Not found"); 
        }

        return metrics; 
    }

    public void AddMetrics(Metrics metrics){
        metricsRepository.save(metrics); 
    }

    public void updateMetrics(Integer id, Metrics updateMetrics){
        Metrics metrics = metricsRepository.findMetricsById(id); 
        if(metrics == null){
            throw new ApiException("Metrics with this ID: " + id + ", Not found"); 
        }

        metricsRepository.save(metrics); 
    }

    public void deleteMetrics(Integer id){
        Metrics metrics = metricsRepository.findMetricsById(id); 
        if(metrics == null){
            throw new ApiException("Metrics with this ID: " + id + ", Not found"); 
        }

        metricsRepository.delete(metrics);
    }
}
