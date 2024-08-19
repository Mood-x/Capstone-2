package com.example.capstone_2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstone_2.Model.CommunityExplore;

@Repository
public interface CommunityExploreRepository extends JpaRepository<CommunityExplore, Integer>{

    
}
