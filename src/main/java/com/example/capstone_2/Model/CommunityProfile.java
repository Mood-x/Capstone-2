package com.example.capstone_2.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CommunityProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "communityProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resource> resources;

    @OneToMany(mappedBy = "communityProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Saved> saved;

    @ManyToOne
    @JoinColumn(name = "community_explore_id")
    @JsonIgnore
    private CommunityExplore communityExplore; 

    private String username; 
    
    private String description; 

    private List<Integer> followers = new ArrayList<>();
    
    private List<Integer> following = new ArrayList<>(); 


}
