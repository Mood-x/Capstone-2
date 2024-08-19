package com.example.capstone_2.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.example.capstone_2.API.ApiException;
import com.example.capstone_2.API.ApiResponse;
import com.example.capstone_2.Model.Notification;
import com.example.capstone_2.Model.Plan;
import com.example.capstone_2.Model.Team;
import com.example.capstone_2.Model.User;
import com.example.capstone_2.Repository.TeamRepository;
import com.example.capstone_2.Repository.UserRepository;
import com.example.capstone_2.Service.NotificationService;
import com.example.capstone_2.Service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository; 


// ========================= [ GET ALL USERS ] ==========================
    @GetMapping
    public ResponseEntity getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers()); 
    }


// =================== [ GET USER BY ID ] ===============================
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(userService.getUserById(id)); 
    }


    // =================== [ GET USER BY USERNAME ] ===============================
    @GetMapping("/username/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username){
        return ResponseEntity.status(200).body(userService.getUserByUsername(username)); 
    }


    // =================== [ GET USER BY EMAIL ] ===============================
    @GetMapping("/email/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email){
        return ResponseEntity.status(200).body(userService.getUserByEmail(email)); 
    }

    

    // +19
// ============================= [ CREATE ] =============================
    @PostMapping("/add")
    public ResponseEntity addUser(@Valid @RequestBody User user, Errors err){
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage(); 
            return ResponseEntity.status(400).body(message); 
        }

        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully")); 
    }

// ============================= [ UPDATE ] =============================
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @Valid @RequestBody User user, Errors err){
        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage(); 
            return ResponseEntity.status(400).body(message); 
        }
        userService.updateUser(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully")); 
    }

// ============================= [ DELETE ] =============================
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully")); 
    }


// ============================= [ Transaction ] =============================

    // 12
    @PostMapping("/addBalance/{userId}/{amount}")
    public ResponseEntity addFunds(@PathVariable Integer userId, @PathVariable double amount){
        userService.addFunds(userId, amount); 
        return ResponseEntity.status(200).body(new ApiResponse("Added amount " + amount + "to wallet" + userId)); 
    }


    // +13
    @PutExchange("/transfer/{payerId}/{payeeId}/{amount}")
    public ResponseEntity transferFunds(@PathVariable Integer payerId, @PathVariable Integer payeeId, @PathVariable double amount){
        userService.transferFunds(payerId, payeeId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Transaction " + amount + " from " + payerId + " to "  + payeeId)); 
    }


    // +14
    @PostMapping("/{userId}/subscribe")
    public ResponseEntity subscribeUser(@PathVariable Integer userId, @RequestParam Plan.SubPlan planType){
        userService.subscribeUser(userId, planType);
        return ResponseEntity.status(200).body("Subscribe Successfully"); 
    }


    // +15
    // ======================================================================
    @PostMapping("/{userId}/invite-to-team/{teamId}")
    public ResponseEntity inviteUserToTeam(@PathVariable Integer userId, @PathVariable Integer teamId){
        User user = userRepository.findUserById(userId);
        if(user == null){
            throw new ApiException("User not found"); 
        }

        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new ApiException("Team not found")); 
        
        Notification notification = notificationService.createNotification(user, team); 
        return ResponseEntity.status(200).body(notification); 
    }

    // +16
    @GetMapping("/{userId}/notifications")
    public ResponseEntity getUserNotification(@PathVariable Integer userId, @RequestParam Notification.NotificationStatus status){
        List<Notification> notifications = notificationService.getNotificationsByUserAndStatus(userId, status); 

        return ResponseEntity.status(200).body(notifications); 
    }

    // +17
    @PutMapping("/notifications/{notificationId}/approve")
    public ResponseEntity approveNotification(@PathVariable Integer notificationId){
        userService.approveNotification(notificationId); 
        return ResponseEntity.status(200).body("Approved"); 
    }

    // +18
    @PutMapping("/notifications/{notificationId}/reject")
    public ResponseEntity rejectNotification(@PathVariable Integer notificationId){
        userService.approveNotification(notificationId); 
        return ResponseEntity.status(200).body("Rejected"); 
    }
    
}
