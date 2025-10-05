package com.aiu.trips.model;

import com.aiu.trips.enums.NotificationType;
import com.aiu.trips.enums.NotificationStatus;
import jakarta.persistence.*;
// Lombok temporarily removed due to Java 25 compatibility
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    
    public Notification() {}
    
    public Notification(Long id, User user, String message, String type, Boolean isRead, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(nullable = false, length = 2000)
    private String message;
    
    @Column
    private String subject;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType; // EMAIL, SMS, PUSH, IN_APP
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus status; // PENDING, SENT, FAILED, READ
    
    @Column
    private Boolean isRead;
    
    @Column
    private LocalDateTime sentAt;
    
    @Column
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        isRead = false;
        if (status == null) {
            status = NotificationStatus.PENDING;
        }
        if (notificationType == null) {
            notificationType = NotificationType.EMAIL;
        }
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public NotificationType getNotificationType() { return notificationType; }
    public void setNotificationType(NotificationType notificationType) { this.notificationType = notificationType; }
    
    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }
    
    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }
    
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
