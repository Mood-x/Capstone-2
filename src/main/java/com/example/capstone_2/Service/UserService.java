package com.example.capstone_2.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capstone_2.API.ApiException;
import com.example.capstone_2.Model.CommunityExplore;
import com.example.capstone_2.Model.CommunityProfile;
import com.example.capstone_2.Model.Notification;
import com.example.capstone_2.Model.Notification.NotificationStatus;
import com.example.capstone_2.Model.Plan;
import com.example.capstone_2.Model.Team;
import com.example.capstone_2.Model.Transaction;
import com.example.capstone_2.Model.User;
import com.example.capstone_2.Repository.CommunityProfileRepository;
import com.example.capstone_2.Repository.PlanRepository;
import com.example.capstone_2.Repository.TeamRepository;
import com.example.capstone_2.Repository.TransactionRepository;
import com.example.capstone_2.Repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository; 
    private final PlanRepository planRepository;
    private final CommunityProfileRepository communityProfileRepository; 
    private final CommunityExploreService communityExploreService; 
    private final PlanService planService;
    private final NotificationService notificationService; 
    private final TeamRepository teamRepository; 


// ========================= [ GET ALL USERS ] ==========================
    public List<User> getUsers(){
        if(userRepository.findAll().isEmpty()){
            throw new ApiException("Not found users"); 
        }
        return userRepository.findAll(); 
    }


// =================== [ GET USER BY ID ] ===============================
    public User getUserById(Integer id){
        User user = userRepository.findUserById(id); 
        if(user == null){
            throw new ApiException("User with this ID: " + id + ", Not found"); 
        }
        return user; 
    }


// =================== [ GET USER BY USERNAME ] ===============================
    public User getUserByUsername(String username){
        User user = userRepository.findUserByUsername(username); 
        if(user == null){
            throw new ApiException("User with this Username: " + username + ", Not found"); 
        }
        return user; 
    }


// =================== [ GET USER BY EMAIL ] ===============================
    public User getUserByEmail(String email){
        User user = userRepository.findUserByEmail(email); 
        if(user == null){
            throw new ApiException("User with this Email: " + email + ", Not found"); 
        }
        return user; 
    }


// ============================= [ CREATE ] =============================
    public void addUser(User user){
        planService.init();
        User savedUser = userRepository.save(user); 
        Plan defaultPlan = planRepository.findPlanByType(Plan.SubPlan.STARTER); 
        if(defaultPlan == null){
            throw new ApiException("Default plan not found"); 
        }

        savedUser.setPlan(defaultPlan);
        userRepository.save(savedUser); 
    }


// ============================= [ UPDATE ] =============================
    public void updateUser(Integer id, User updateUser){
        User user = userRepository.findUserById(id); 

        if(user == null){
            throw new ApiException("User with this ID: " + id + ", Not found"); 
        }

        user.setUsername(updateUser.getUsername());
        user.setEmail(updateUser.getEmail());
        user.setPassword(updateUser.getPassword());
        userRepository.save(user); 
    }


// ============================= [ DELETE ] =============================
    public void deleteUser(Integer id){
        User user = userRepository.findUserById(id); 
        if(user == null){
            throw new ApiException("User with this ID: " + id + ", Not found"); 
        }

        userRepository.delete(user);
    }


// ========== Transaction ===============

    @Transactional
    public User addFunds(Integer userId, double amount){
        User user = userRepository.findUserById(userId);
        if(user == null){
            throw new ApiException("User with this ID: " + userId + ", Not found"); 
        }

        user.setBalance(user.getBalance() + amount);
        return userRepository.save(user); 
    }

    @Transactional
    public void transferFunds(Integer payerId, Integer payeeId, double amount){
        User payer = userRepository.findUserById(payerId); 
        User payee = userRepository.findUserById(payeeId); 
        if(payer == null || payee == null){
            throw new ApiException(
                payer == null ? "User with this ID: " + payerId : payeeId + ", Not found"); 
        }

        if(payer.getBalance() >= amount){
            payer.setBalance(payer.getBalance() - amount);
            payee.setBalance(payee.getBalance() + amount);

            userRepository.save(payer); 
            userRepository.save(payee);

            Transaction transaction = new Transaction(); 

            transaction.setPayer(payer);
            transaction.setPayee(payee);
            transaction.setAmount(amount);

            transactionRepository.save(transaction); 
        }else {
            throw new ApiException("Insufficient funds"); 
        }
    }



    @Transactional
    public void subscribeUser(Integer userId, Plan.SubPlan planType){
        User user = userRepository.findUserById(userId);
        Plan plan = planRepository.findPlanByType(planType); 

        if(user == null){
            throw new ApiException("Not found user"); 
        }

        if(plan == null){
            throw new ApiException("Not found plan"); 
        }

        user.setPlan(plan);
        CommunityProfile communityProfile = user.getCommunityProfile(); 

        if(communityProfile == null && planType == Plan.SubPlan.PROFESSIONAL){
            CommunityExplore communityExplore = communityExploreService.getDefaultCommunityExplore(); 
            communityProfile = new CommunityProfile(); 
            communityProfile.setUsername(user.getUsername());
            communityProfile.setUser(user);
            communityProfile.setCommunityExplore(communityExplore);
            communityProfileRepository.save(communityProfile); 
            user.setCommunityProfile(communityProfile);
        }
        user.setBalance(user.getBalance() - 50.0);
        userRepository.save(user); 
    }



    // ===========================================================================

    // Features 
    public boolean canCreateProject(User user){
        if(user.getPlan() != null && user.getPlan().getType() == Plan.SubPlan.STARTER){
            return user.getProjects().size() < 3; 
        }
        return true; 
    }

    public boolean canCreateCommunityProfile(User user){
        return user.getPlan() == null || user.getPlan().getType() == Plan.SubPlan.PROFESSIONAL; 
    }

    public boolean canCreateTeam(User user){
        return user.getPlan() == null || user.getPlan().getType() == Plan.SubPlan.PROFESSIONAL; 
    }


    // ============================================================================

    public void approveNotification(Integer notificationId){
        Notification notification = notificationService.updateNotificationStatus(notificationId, NotificationStatus.APPROVED); 

        Team team = notification.getTeam(); 
        User user = notification.getRecipient(); 

        if(!team.getMembers().contains(user)){
            team.getMembers().add(user); 
            teamRepository.save(team); 
        }
    }
}
