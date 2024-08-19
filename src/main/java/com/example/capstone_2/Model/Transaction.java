package com.example.capstone_2.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @ManyToOne
    @JoinColumn(name = "payer_id")
    @JsonIgnore
    private User payer; 

    @ManyToOne
    @JoinColumn(name = "payee_id")
    @JsonIgnore
    private User payee; 

    @Column(columnDefinition = "decimal(10, 2) not null")
    private double amount; 

    @Column(nullable = false)
    private LocalDateTime timestamp; 

    @PrePersist
    public void PrePersist(){
        timestamp = LocalDateTime.now(); 
    }

}
