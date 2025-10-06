package com.aiu.trips.service;

import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Notification;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.NotificationRepository;
import com.aiu.trips.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public void notifyUser(Long userId, String message, String type) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notificationRepository.save(notification);
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

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
    
    /**
     * Send email notification (placeholder implementation)
     * In production, this would integrate with an email service like SendGrid, AWS SES, etc.
     */
    public boolean sendEmail(String recipient, String subject, String body) {
        // Log the email for now
        System.out.println("=== Email Notification ===");
        System.out.println("To: " + recipient);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("========================");
        
        // In production, integrate with actual email service
        // For now, just create a notification record
        User user = userRepository.findByEmail(recipient).orElse(null);
        if (user != null) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMessage(subject + ": " + body);
            notification.setType("EMAIL");
            notificationRepository.save(notification);
        }
        
        return true;
    }
}
