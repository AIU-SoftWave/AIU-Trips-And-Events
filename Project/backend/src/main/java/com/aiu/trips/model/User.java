package com.aiu.trips.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false)
    private String role; // STUDENT, ORGANIZER, ADMINISTRATOR
    
    @Column
    private String phoneNumber;
    
    @Column
    private String faculty;
    
    @Column
    private String academicYear;
    
    @Column
    private Boolean emailVerified = false;
    
    @Column
    private String verificationToken;
    
    @Column
    private String resetPasswordToken;
    
    @Column
    private LocalDateTime resetPasswordExpiry;
    
    @Column
    private Integer failedLoginAttempts = 0;
    
    @Column
    private Boolean accountLocked = false;
    
    @Column
    private LocalDateTime lockedUntil;
    
    @Column
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (emailVerified == null) {
            emailVerified = false;
        }
        if (failedLoginAttempts == null) {
            failedLoginAttempts = 0;
        }
        if (accountLocked == null) {
            accountLocked = false;
        }
        if (fullName == null && firstName != null && lastName != null) {
            fullName = firstName + " " + lastName;
        }
    }
}
