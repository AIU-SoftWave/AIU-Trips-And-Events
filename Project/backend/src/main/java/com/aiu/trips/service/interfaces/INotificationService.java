package com.aiu.trips.service.interfaces;

import com.aiu.trips.model.Notification;

import java.util.List;

public interface INotificationService {
    boolean sendNotification(Long userId, String message, String type);
    boolean sendEmailNotification(String email, String message);
    List<Notification> getUserNotifications(Long userId);
    boolean markAsRead(Long notificationId);
}
