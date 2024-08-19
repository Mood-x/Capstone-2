package com.example.capstone_2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstone_2.Model.Plan;

@Repository
public interface  PlanRepository extends JpaRepository<Plan, Integer>{

    Plan findPlanById(Integer id);
    Plan findPlanByType(Plan.SubPlan planType); 
}
