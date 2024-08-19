package com.example.capstone_2.Model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username should be not empty")
    @Size(min = 3, max = 25, message = "Username must be between 3 and 25 characters")
    @Column(length = 25, nullable = false, unique = true)
    private String username; 

    @NotEmpty(message = "Email should be not empty")
    @Email(message = "Invalid email format")
    @Column(length = 25, nullable = false, unique = true)
    private String email; 

    @NotEmpty(message = "Password should be not empty")
    @Size(min = 6, max = 25, message = "Password must be between 6 and 25 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,25}$", message = "Password must be between 6 and 25")
    @Column(length = 25, nullable = false)
    private String password; 

    // @Enumerated(EnumType.STRING)
    // private Set<Role> roles = new HashSet<>(); 

    @PositiveOrZero(message = "Balance must be zero or positive")
    @Column(columnDefinition = "decimal(10, 2) default 0.0")
    private double balance = 0.0; 

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt; 

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt; 
    

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects; 


    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan; 

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CommunityProfile communityProfile; 

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team; 

    // public enum Role {
    //         USER,
    //         ADMIN,
    //         VIEWER, 
    //         GUEST
    //     }

}




