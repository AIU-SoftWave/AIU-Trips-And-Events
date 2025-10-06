package com.aiu.trips.service;

import com.aiu.trips.dto.RegisterRequest;
import com.aiu.trips.model.User;
import com.aiu.trips.enums.UserRole;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.exception.ResourceNotFoundException;
import com.aiu.trips.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private NotificationService notificationService;
    
    /**
     * Register a new user with email verification
     */
    @Transactional
    public User registerUser(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ValidationException("Email already registered");
        }
        
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(UserRole.STUDENT); // Default role
        user.setFaculty(request.getFaculty());
        user.setAcademicYear(request.getAcademicYear());
        user.setIsEmailVerified(false);
        user.setIsActive(true);
        
        // Generate email verification token
        String verificationToken = UUID.randomUUID().toString();
        user.setEmailVerificationToken(verificationToken);
        
        User savedUser = userRepository.save(user);
        
        // Send verification email
        sendVerificationEmail(savedUser, verificationToken);
        
        return savedUser;
    }
    
    /**
     * Verify user email with token
     */
    @Transactional
    public boolean verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
            .orElseThrow(() -> new ValidationException("Invalid verification token"));
        
        user.setIsEmailVerified(true);
        user.setEmailVerificationToken(null);
        userRepository.save(user);
        
        return true;
    }
    
    /**
     * Initiate password reset
     */
    @Transactional
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        String resetToken = UUID.randomUUID().toString();
        user.setPasswordResetToken(resetToken);
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusMinutes(30));
        userRepository.save(user);
        
        // Send password reset email
        sendPasswordResetEmail(user, resetToken);
    }
    
    /**
     * Reset password with token
     */
    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
            .orElseThrow(() -> new ValidationException("Invalid reset token"));
        
        if (user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Reset token has expired");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);
        
        // Unlock account if locked
        user.setIsAccountLocked(false);
        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
        
        userRepository.save(user);
        
        // Send confirmation email
        sendPasswordResetConfirmation(user);
        
        return true;
    }
    
    /**
     * Update user profile
     */
    @Transactional
    public User updateUser(Long userId, RegisterRequest updateData) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        if (updateData.getFullName() != null) {
            user.setFullName(updateData.getFullName());
        }
        if (updateData.getFirstName() != null) {
            user.setFirstName(updateData.getFirstName());
        }
        if (updateData.getLastName() != null) {
            user.setLastName(updateData.getLastName());
        }
        if (updateData.getPhoneNumber() != null) {
            user.setPhoneNumber(updateData.getPhoneNumber());
        }
        if (updateData.getFaculty() != null) {
            user.setFaculty(updateData.getFaculty());
        }
        if (updateData.getAcademicYear() != null) {
            user.setAcademicYear(updateData.getAcademicYear());
        }
        
        return userRepository.save(user);
    }
    
    /**
     * Get user by ID
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    /**
     * Get user by email
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    /**
     * Get all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Delete user
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }
    
    /**
     * Lock user account
     */
    @Transactional
    public void lockAccount(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        user.setIsAccountLocked(true);
        user.setLockedUntil(LocalDateTime.now().plusHours(24));
        userRepository.save(user);
    }
    
    /**
     * Unlock user account
     */
    @Transactional
    public void unlockAccount(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        user.setIsAccountLocked(false);
        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
        userRepository.save(user);
    }
    
    /**
     * Increment failed login attempts
     */
    @Transactional
    public void incrementFailedLoginAttempts(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            
            if (user.getFailedLoginAttempts() >= 5) {
                user.setIsAccountLocked(true);
                user.setLockedUntil(LocalDateTime.now().plusHours(1));
            }
            
            userRepository.save(user);
        }
    }
    
    /**
     * Reset failed login attempts
     */
    @Transactional
    public void resetFailedLoginAttempts(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            user.setFailedLoginAttempts(0);
            userRepository.save(user);
        }
    }
    
    /**
     * Check if account is locked
     */
    public boolean isAccountLocked(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return false;
        }
        
        if (user.getIsAccountLocked() && user.getLockedUntil() != null) {
            if (user.getLockedUntil().isBefore(LocalDateTime.now())) {
                // Auto-unlock if lock period has passed
                unlockAccount(user.getId());
                return false;
            }
            return true;
        }
        
        return false;
    }
    
    // Email notification methods
    private void sendVerificationEmail(User user, String token) {
        String subject = "Verify Your Email - AIU Events";
        String body = String.format("Please verify your email by clicking this link: " +
            "http://localhost:3000/verify-email?token=%s", token);
        notificationService.sendEmail(user.getEmail(), subject, body);
    }
    
    private void sendPasswordResetEmail(User user, String token) {
        String subject = "Password Reset - AIU Events";
        String body = String.format("Reset your password by clicking this link (valid for 30 minutes): " +
            "http://localhost:3000/reset-password?token=%s", token);
        notificationService.sendEmail(user.getEmail(), subject, body);
    }
    
    private void sendPasswordResetConfirmation(User user) {
        String subject = "Password Reset Successful - AIU Events";
        String body = "Your password has been successfully reset.";
        notificationService.sendEmail(user.getEmail(), subject, body);
    }
}
