package com.example.capstone_2.Model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 


    @Column(columnDefinition = "varchar(100) default 'Untitled'")
    private String title = "Untitled";

    @Column(length = 500, nullable = true)
    private String description; 

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('FIGMA', 'FIGJAM', 'SLIDE') not null")
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('DRAFT','PUBLISHED') default 'DRAFT'")
    private Status status = Status.DRAFT; 

    @PositiveOrZero
    @Column(columnDefinition = "double not null")
    private double price;


    @Column(columnDefinition = "timestamp default null")
    private LocalDateTime publishedAt;  

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt; 

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;
    private int likesCount = 0; 

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments; 

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user; 

    @ManyToOne
    @JoinColumn(name = "community_profile_id")
    @JsonIgnore
    private CommunityProfile communityProfile; 


    @ManyToOne
    @JoinColumn(name = "community_explore_id")
    @JsonIgnore
    private CommunityExplore communityExplore; 

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team; 

    public enum Type{
        FIGMA, FIGJAM, SLIDE
    }

    public enum Status{
        DRAFT, PUBLISHED
    }
}
