package com.aiu.trips.service;

import com.aiu.trips.constants.AppConstants;
import com.aiu.trips.dto.AuthResponse;
import com.aiu.trips.dto.LoginRequest;
import com.aiu.trips.dto.RegisterRequest;
import com.aiu.trips.enums.UserRole;
import com.aiu.trips.exception.ResourceNotFoundException;
import com.aiu.trips.exception.ValidationException;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setFullName(request.getFirstName() + " " + request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setFaculty(request.getFaculty());
        user.setAcademicYear(request.getAcademicYear());
        user.setRole(UserRole.STUDENT);
        user.setEmailVerified(false);
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(0);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getEmail(), user.getFullName(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + request.getEmail()));
        
        // Check if account is locked
        if (user.getAccountLocked() != null && user.getAccountLocked()) {
            throw new ValidationException("Account is locked. Please reset your password.");
        }
        
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            // Reset failed login attempts on successful login
            if (user.getFailedLoginAttempts() != null && user.getFailedLoginAttempts() > 0) {
                user.setFailedLoginAttempts(0);
                userRepository.save(user);
            }
            
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            return new AuthResponse(token, user.getEmail(), user.getFullName(), user.getRole().name());
            
        } catch (Exception e) {
            // Increment failed login attempts
            int attempts = user.getFailedLoginAttempts() != null ? user.getFailedLoginAttempts() : 0;
            attempts++;
            user.setFailedLoginAttempts(attempts);
            
            // Lock account after 5 failed attempts
            if (attempts >= 5) {
                user.setAccountLocked(true);
                userRepository.save(user);
                throw new ValidationException("Account locked due to multiple failed login attempts. Please reset your password.");
            }
            
            userRepository.save(user);
            throw new ValidationException("Invalid credentials. Attempt " + attempts + " of 5.");
        }
    }
    
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + email));
        
        // Generate reset token (simple UUID for now)
        String resetToken = java.util.UUID.randomUUID().toString();
        user.setPasswordResetToken(resetToken);
        user.setPasswordResetTokenExpiry(java.time.LocalDateTime.now().plusMinutes(30));
        
        userRepository.save(user);
        
        // TODO: Send email with reset token
        // For now, token would be returned or logged
    }
    
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findAll().stream()
            .filter(u -> token.equals(u.getPasswordResetToken()))
            .findFirst()
            .orElseThrow(() -> new ValidationException("Invalid or expired reset token"));
        
        // Check if token is expired
        if (user.getPasswordResetTokenExpiry() == null || 
            java.time.LocalDateTime.now().isAfter(user.getPasswordResetTokenExpiry())) {
            throw new ValidationException("Reset token has expired");
        }
        
        // Reset password and unlock account
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(0);
        
        userRepository.save(user);
    }
}
