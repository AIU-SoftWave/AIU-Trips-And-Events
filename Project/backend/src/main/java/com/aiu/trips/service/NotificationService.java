package com.aiu.trips.service;

import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Notification;
import com.aiu.trips.model.User;
import com.aiu.trips.model.Payment;
import com.aiu.trips.model.Ticket;
import com.aiu.trips.enums.NotificationType;
import com.aiu.trips.enums.NotificationStatus;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.NotificationRepository;
import com.aiu.trips.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        notification.setNotificationType(NotificationType.IN_APP);
        notification.setStatus(NotificationStatus.SENT);
        notificationRepository.save(notification);
    }

    public void notifyAllUsers(String message, String type) {
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.IN_APP);
            notification.setStatus(NotificationStatus.SENT);
            notificationRepository.save(notification);
        });
    }

    public void notifyEventParticipants(Long eventId, String message, String type) {
        List<Booking> bookings = bookingRepository.findByEvent_Id(eventId);
        bookings.forEach(booking -> {
            Notification notification = new Notification();
            notification.setUser(booking.getUser());
            notification.setMessage(message);
            notification.setNotificationType(NotificationType.IN_APP);
            notification.setStatus(NotificationStatus.SENT);
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
        notification.setStatus(NotificationStatus.READ);
        notificationRepository.save(notification);
    }
    
    // New methods for enhanced notification functionality
    
    public void sendEmailVerification(User user, String verificationToken) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setSubject("Email Verification - AIU Trips and Events");
        notification.setMessage("Please verify your email by clicking the link: /verify-email?token=" + verificationToken);
        notification.setNotificationType(NotificationType.EMAIL);
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
        
        // In a real implementation, this would integrate with an email service provider
        System.out.println("Email verification sent to: " + user.getEmail());
    }
    
    public void sendPasswordResetEmail(User user, String resetToken) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setSubject("Password Reset - AIU Trips and Events");
        notification.setMessage("Reset your password by clicking the link: /reset-password?token=" + resetToken);
        notification.setNotificationType(NotificationType.EMAIL);
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
        
        // In a real implementation, this would integrate with an email service provider
        System.out.println("Password reset email sent to: " + user.getEmail());
    }
    
    public void sendTicketEmail(User user, Booking booking, Ticket ticket) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setSubject("Your Event Ticket - " + booking.getEvent().getTitle());
        notification.setMessage("Your ticket for " + booking.getEvent().getTitle() + " has been generated. Ticket Code: " + ticket.getTicketCode());
        notification.setNotificationType(NotificationType.EMAIL);
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
        
        System.out.println("Ticket email sent to: " + user.getEmail());
    }
    
    public void sendPaymentConfirmation(User user, Booking booking, Payment payment) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setSubject("Payment Confirmation - " + booking.getEvent().getTitle());
        notification.setMessage("Your payment of $" + payment.getAmount() + " has been processed successfully. Transaction ID: " + payment.getTransactionId());
        notification.setNotificationType(NotificationType.EMAIL);
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
        
        System.out.println("Payment confirmation sent to: " + user.getEmail());
    }
    
    public void sendRefundNotification(User user, Booking booking, Payment payment) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setSubject("Refund Processed - " + booking.getEvent().getTitle());
        notification.setMessage("Your refund of $" + payment.getAmount() + " has been processed. Reason: " + payment.getRefundReason());
        notification.setNotificationType(NotificationType.EMAIL);
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
        
        System.out.println("Refund notification sent to: " + user.getEmail());
    }
    
    public void sendEventReminder(User user, Booking booking) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setSubject("Event Reminder - " + booking.getEvent().getTitle());
        notification.setMessage("Reminder: " + booking.getEvent().getTitle() + " is happening soon on " + booking.getEvent().getStartDate());
        notification.setNotificationType(NotificationType.EMAIL);
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
        
        System.out.println("Event reminder sent to: " + user.getEmail());
    }
}

