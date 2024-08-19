package com.example.capstone_2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Metrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int views; 
    private int likes; 
    private int shares; 
    private int comments; 

    private double totalEarnings = 0; 
    private int purchases = 0;
    
    @ManyToOne
    @JoinColumn(name = "communityProfile_id")
    @JsonIgnore
    private CommunityProfile communityProfile; 
}
