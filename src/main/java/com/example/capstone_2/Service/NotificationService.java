package com.example.capstone_2.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capstone_2.API.ApiException;
import com.example.capstone_2.Model.Notification;
import com.example.capstone_2.Model.Notification.NotificationStatus;
import com.example.capstone_2.Model.Team;
import com.example.capstone_2.Model.User;
import com.example.capstone_2.Repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository; 

    public List<Notification> getNotificationsByUserAndStatus(Integer userId, Notification.NotificationStatus status){
        return notificationRepository.findNotificationsByRecipientIdAndStatus(userId, null); 
    }

    public Notification createNotification(User recipient, Team team){
        Notification notification = new Notification(); 

        notification.setRecipient(recipient);
        notification.setTeam(team);
        notification.setStatus(NotificationStatus.PENDING);
        return notificationRepository.save(notification); 
    }

    public Notification updateNotificationStatus(Integer notificationId, Notification.NotificationStatus status){
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new ApiException("Notification not found")); 

        notification.setStatus(status);
        return notificationRepository.save(notification); 


    }
}
