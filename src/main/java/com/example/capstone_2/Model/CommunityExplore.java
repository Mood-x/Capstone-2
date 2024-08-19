package com.example.capstone_2.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CommunityExplore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @OneToMany(mappedBy = "communityExplore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityProfile> communityProfiles;

    private String name;
    
    @OneToMany(mappedBy = "communityExplore")
    private List<Project> projects; 
}
