package com.example.capstone_2.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capstone_2.API.ApiException;
import com.example.capstone_2.Model.Plan;
import com.example.capstone_2.Repository.PlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository; 


        public void init(){
            if (planRepository.findPlanByType(Plan.SubPlan.STARTER) == null) {
                Plan starterPlan = new Plan();
                starterPlan.setType(Plan.SubPlan.STARTER);
                starterPlan.setPrice(0.0);  // الخطة مجانية
                starterPlan.setMaxPersonalDrafts(3);  // الحد الأقصى لعدد المسودات الشخصية هو 3
                starterPlan.setActive(true);  // الخطة نشطة
                planRepository.save(starterPlan);
            }
        
            if (planRepository.findPlanByType(Plan.SubPlan.PROFESSIONAL) == null) {
                Plan professionalPlan = new Plan();
                professionalPlan.setType(Plan.SubPlan.PROFESSIONAL);
                professionalPlan.setPrice(12.0);  // الخطة مدفوعة بقيمة 12 دولار
                professionalPlan.setMaxPersonalDrafts(Integer.MAX_VALUE);  // الحد الأقصى لعدد المسودات الشخصية غير محدود
                professionalPlan.setActive(true);  // الخطة نشطة
                planRepository.save(professionalPlan);
            }
        }

    public List<Plan> getPlans(){
        if(planRepository.findAll().isEmpty()){
            throw new ApiException("Not found Plans"); 
        }
        return planRepository.findAll(); 
    }

    public Plan getPlanById(Integer id) {
        Plan plan = planRepository.findPlanById(id); 
        if(plan == null){
            throw new ApiException("Plan with this ID: " + id + ", Not found"); 
        }
        return plan; 
    }


    public void addPlan(Plan plan){
        planRepository.save(plan); 
    }

    public void updatePlan(Integer id, Plan updatePlan){
        Plan plan = planRepository.findPlanById(id);
        if(plan == null){
            throw new ApiException("Plan with this ID: " + id + ", Not found"); 
        }

        plan.setType(updatePlan.getType());
        plan.setPrice(updatePlan.getPrice());
        plan.setDescription(updatePlan.getDescription());
        plan.setMaxPersonalDrafts(updatePlan.getMaxPersonalDrafts());

        planRepository.save(plan); 
    }

    public void deletePlan(Integer id){
        Plan plan = planRepository.findPlanById(id); 
        if(plan == null){
            throw new ApiException("Plan with this ID: " + id + ", Not found"); 
        }

        planRepository.save(plan); 
    }
}
