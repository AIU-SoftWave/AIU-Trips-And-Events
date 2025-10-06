package com.aiu.trips.service;

import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Notification;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.NotificationRepository;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public boolean sendNotification(Long userId, String message, String type) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notificationRepository.save(notification);
        return true;
    }

    public void notifyUser(Long userId, String message, String type) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notificationRepository.save(notification);
    }

    @Override
    public boolean sendEmailNotification(String email, String message) {
        // TODO: Implement actual email sending logic
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType("EMAIL");
        notificationRepository.save(notification);
        return true;
    }

    public void notifyAllUsers(String message, String type) {
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMessage(message);
            notification.setType(type);
            notificationRepository.save(notification);
        });
    }

    public void notifyEventParticipants(Long eventId, String message, String type) {
        List<Booking> bookings = bookingRepository.findByEvent_Id(eventId);
        bookings.forEach(booking -> {
            Notification notification = new Notification();
            notification.setUser(booking.getUser());
            notification.setMessage(message);
            notification.setType(type);
            notificationRepository.save(notification);
        });
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUser_IdOrderByCreatedAtDesc(userId);
    }

    public List<Notification> getUserNotifications(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByUser_IdOrderByCreatedAtDesc(user.getId());
    }

    public List<Notification> getUnreadNotifications(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByUser_IdAndIsReadFalse(user.getId());
    }

    @Override
    public boolean markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
        return true;
    }

    public void markAsReadOld(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
}
