package com.example.capstone_2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstone_2.Model.CommunityProfile;
import com.example.capstone_2.Model.User;

@Repository
public interface CommunityProfileRepository extends JpaRepository<CommunityProfile, Integer>{

    CommunityProfile findCommunityProfileByUsername(String username);
    CommunityProfile findCommunityProfileByUserId(Integer id); 
    CommunityProfile findCommunityProfileByUser(User user); 
}
