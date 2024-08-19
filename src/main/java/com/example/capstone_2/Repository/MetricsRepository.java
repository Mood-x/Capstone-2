package com.example.capstone_2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstone_2.Model.Metrics;

@Repository
public interface  MetricsRepository extends JpaRepository<Metrics, Integer>{

    Metrics findMetricsById(Integer id); 
}
