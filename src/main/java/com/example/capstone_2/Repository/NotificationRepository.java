package com.example.capstone_2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstone_2.Model.Notification;
import com.example.capstone_2.Model.Notification.NotificationStatus;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>{

    List<Notification> findNotificationsByRecipientIdAndStatus(Integer recipientId, NotificationStatus status); 
}
