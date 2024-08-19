package com.example.capstone_2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstone_2.Model.Project;

@Repository
public interface  ProjectRepository extends JpaRepository<Project, Integer>{

    Project findProjectById(Integer id);
    List<Project> findProjectsByUserId(Integer userId);
    List<Project> findProjectByStatus(Project.Status status);
    @Query("SELECT p FROM Project p WHERE p.publichedAt BETWEEN :startDate AND :endDate")
    List<Project> findProjectByPublishedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
