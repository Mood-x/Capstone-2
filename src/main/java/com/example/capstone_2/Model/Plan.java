package com.example.capstone_2.Model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.capstone_2.API.ApiException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Plan type should not be null")
    @Enumerated(EnumType.STRING)
    private SubPlan type;

    @NotNull
    @PositiveOrZero
    @Column(columnDefinition = "decimal(10, 2) not null")
    private double price;

    @Column(columnDefinition = "text")
    private String description; 

    @NotNull
    @PositiveOrZero
    @Column(columnDefinition = "int default 0")
    private int maxPersonalDrafts;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp", updatable = false)
    private LocalDateTime createdAt;

    
    @Column(nullable = true) 
    private LocalDateTime startDate; 

    @Future(message = "End date must be in the future")
    @Column(nullable = true)
    private LocalDateTime endDate; 

    @Column(nullable = false)
    private boolean isActive; 


    public void setActive(boolean active){
        this.isActive = active;

        // Only update dates if the plan is not FREE and has a startDate
        if (active && this.startDate != null && !isFreePlan()) {
            updateEndDate(); 
        } else {
            this.endDate = null; // Optionally, clear endDate if inactive or for free plans
        }
    }

    private void updateEndDate(){
        if (this.startDate == null) {
            throw new ApiException("Start date must be initialized before calculating end date."); 
        }
        this.endDate = this.startDate.plusMonths(1); 
    }

    @PrePersist
    @PreUpdate
    private void ensureEndDateIsCalculated(){
        if (this.isActive && this.startDate != null && !isFreePlan()) {
            updateEndDate();
        } else {
            this.endDate = null;
        }
    }

    private boolean isFreePlan() {
        return this.type == SubPlan.STARTER && this.price == 0.0;
    }

    public enum SubPlan{
        STARTER, PROFESSIONAL
    }

}
