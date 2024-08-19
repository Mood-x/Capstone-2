package com.example.capstone_2.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Enumerated(EnumType.STRING)
    private NotificationStatus status; 

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User recipient; 

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;


    public enum NotificationStatus {
        PENDING, 
        APPROVED, 
        REJECTED
    }
}
