package com.example.capstone_2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstone_2.Model.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer>{
    Resource findResourcesById(Integer id); 

}
