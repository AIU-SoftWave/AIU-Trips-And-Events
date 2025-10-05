package com.aiu.trips.service;

import com.aiu.trips.constants.AppConstants;
import com.aiu.trips.dto.AuthResponse;
import com.aiu.trips.dto.LoginRequest;
import com.aiu.trips.dto.RegisterRequest;
import com.aiu.trips.enums.UserRole;
import com.aiu.trips.enums.UserStatus;
import com.aiu.trips.exception.ResourceNotFoundException;
import com.aiu.trips.exception.ValidationException;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private NotificationService notificationService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already exists");
        }
        
        // Validate password strength
        validatePasswordStrength(request.getPassword());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(UserRole.STUDENT);
        user.setStatus(UserStatus.PENDING_VERIFICATION);
        user.setEmailVerified(false);
        
        // Set additional fields if provided
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getFaculty() != null) {
            user.setFaculty(request.getFaculty());
        }
        if (request.getAcademicYear() != null) {
            user.setAcademicYear(request.getAcademicYear());
        }
        
        // Generate email verification token
        String verificationToken = generateToken();
        user.setEmailVerificationToken(verificationToken);
        user.setEmailVerificationTokenExpiry(LocalDateTime.now().plusHours(24));

        userRepository.save(user);
        
        // Send verification email
        notificationService.sendEmailVerification(user, verificationToken);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getEmail(), user.getFullName(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + request.getEmail()));
        
        // Check if account is locked
        if (user.isAccountLocked()) {
            throw new ValidationException("Account is locked due to multiple failed login attempts. Please reset your password.");
        }
        
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            // Reset failed login attempts on successful login
            user.setFailedLoginAttempts(0);
            user.setAccountLockedUntil(null);
            userRepository.save(user);
            
        } catch (BadCredentialsException e) {
            // Increment failed login attempts
            int failedAttempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(failedAttempts);
            
            // Lock account after 5 failed attempts
            if (failedAttempts >= 5) {
                user.setAccountLockedUntil(LocalDateTime.now().plusHours(1));
                userRepository.save(user);
                throw new ValidationException("Account locked due to multiple failed login attempts. Please try again after 1 hour or reset your password.");
            }
            
            userRepository.save(user);
            throw new BadCredentialsException("Invalid credentials. Attempts: " + failedAttempts + "/5");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getEmail(), user.getFullName(), user.getRole().name());
    }
    
    public String initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        
        String resetToken = generateToken();
        user.setPasswordResetToken(resetToken);
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusMinutes(30));
        
        userRepository.save(user);
        
        // Send password reset email
        notificationService.sendPasswordResetEmail(user, resetToken);
        
        return "Password reset link sent to your email";
    }
    
    public String resetPassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
            .orElseThrow(() -> new ValidationException("Invalid or expired reset token"));
        
        if (user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Reset token has expired");
        }
        
        // Validate password strength
        validatePasswordStrength(newPassword);
        
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);
        user.setFailedLoginAttempts(0);
        user.setAccountLockedUntil(null);
        
        userRepository.save(user);
        
        return "Password reset successfully";
    }
    
    public String verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
            .orElseThrow(() -> new ValidationException("Invalid verification token"));
        
        if (user.getEmailVerificationTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Verification token has expired");
        }
        
        user.setEmailVerified(true);
        user.setStatus(UserStatus.ACTIVE);
        user.setEmailVerificationToken(null);
        user.setEmailVerificationTokenExpiry(null);
        
        userRepository.save(user);
        
        return "Email verified successfully";
    }
    
    private String generateToken() {
        return UUID.randomUUID().toString();
    }
    
    private void validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters long");
        }
        
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecialChar = password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
        
        if (!hasUpperCase || !hasLowerCase || !hasDigit || !hasSpecialChar) {
            throw new ValidationException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character");
        }
    }
}
