package com.example.capstone_2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.capstone_2.Model.Saved;

@Repository
public interface SavedRepository extends JpaRepository<Saved, Integer>{
    Saved findSavedById(Integer id);  
    @Query("SELECT COUNT(s) > 0 FROM Saved s WHERE s.project.id = :projectId AND s.communityProfile.id = :communityProfileId")
    boolean existsByProjectIdAndCommunityProfileId(@Param("projectId") Integer projectId, @Param("communityProfileId") Integer communityProfileId);

}
