package com.aiu.trips.model;

import com.aiu.trips.enums.UserRole;
import jakarta.persistence.*;
// Lombok temporarily removed due to Java 25 compatibility
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    public User() {}
    
    public User(Long id, String email, String password, String firstName, String lastName, String fullName, UserRole role, String phoneNumber, String faculty, String academicYear, Boolean emailVerified, Boolean accountLocked, Integer failedLoginAttempts, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.faculty = faculty;
        this.academicYear = academicYear;
        this.emailVerified = emailVerified;
        this.accountLocked = accountLocked;
        this.failedLoginAttempts = failedLoginAttempts;
        this.createdAt = createdAt;
    }
    
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
    @Enumerated(EnumType.STRING)
    private UserRole role; // STUDENT, ORGANIZER, ADMIN
    
    @Column
    private String phoneNumber;
    
    @Column
    private String faculty;
    
    @Column
    private String academicYear;
    
    @Column(nullable = false)
    private Boolean emailVerified = false;
    
    @Column(nullable = false)
    private Boolean accountLocked = false;
    
    @Column(nullable = false)
    private Integer failedLoginAttempts = 0;
    
    @Column
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (emailVerified == null) emailVerified = false;
        if (accountLocked == null) accountLocked = false;
        if (failedLoginAttempts == null) failedLoginAttempts = 0;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
    
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    
    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; }
    
    public Boolean getAccountLocked() { return accountLocked; }
    public void setAccountLocked(Boolean accountLocked) { this.accountLocked = accountLocked; }
    
    public Integer getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(Integer failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
