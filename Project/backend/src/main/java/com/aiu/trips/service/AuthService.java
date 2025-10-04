package com.aiu.trips.service;

import com.aiu.trips.dto.AuthResponse;
import com.aiu.trips.dto.LoginRequest;
import com.aiu.trips.dto.RegisterRequest;
import com.aiu.trips.dto.PasswordResetRequest;
import com.aiu.trips.dto.ResetPasswordRequest;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
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
    private EmailService emailService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
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
        user.setRole("STUDENT");
        user.setEmailVerified(false);
        user.setFailedLoginAttempts(0);
        user.setAccountLocked(false);
        
        // Generate verification token
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);

        userRepository.save(user);
        
        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), verificationToken);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getEmail(), user.getFullName(), user.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check if account is locked
        if (user.getAccountLocked() != null && user.getAccountLocked()) {
            if (user.getLockedUntil() != null && LocalDateTime.now().isBefore(user.getLockedUntil())) {
                throw new LockedException("Account is locked due to multiple failed login attempts. Please reset your password.");
            } else {
                // Unlock account if lock period has expired
                user.setAccountLocked(false);
                user.setFailedLoginAttempts(0);
                user.setLockedUntil(null);
                userRepository.save(user);
            }
        }
        
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            // Reset failed attempts on successful login
            if (user.getFailedLoginAttempts() != null && user.getFailedLoginAttempts() > 0) {
                user.setFailedLoginAttempts(0);
                userRepository.save(user);
            }
            
        } catch (BadCredentialsException e) {
            // Increment failed login attempts
            int attempts = (user.getFailedLoginAttempts() != null ? user.getFailedLoginAttempts() : 0) + 1;
            user.setFailedLoginAttempts(attempts);
            
            // Lock account after 5 failed attempts
            if (attempts >= 5) {
                user.setAccountLocked(true);
                user.setLockedUntil(LocalDateTime.now().plusHours(1)); // Lock for 1 hour
                userRepository.save(user);
                throw new LockedException("Account locked due to 5 consecutive failed login attempts. Please reset your password.");
            }
            
            userRepository.save(user);
            throw new BadCredentialsException("Invalid credentials. Attempt " + attempts + " of 5");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getEmail(), user.getFullName(), user.getRole());
    }
    
    public String verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid verification token"));
        
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
        
        return "Email verified successfully";
    }
    
    public String requestPasswordReset(PasswordResetRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Generate reset token
        String resetToken = UUID.randomUUID().toString();
        user.setResetPasswordToken(resetToken);
        user.setResetPasswordExpiry(LocalDateTime.now().plusMinutes(30)); // Valid for 30 minutes
        
        // Unlock account if locked (password reset bypasses lock)
        if (user.getAccountLocked() != null && user.getAccountLocked()) {
            user.setAccountLocked(false);
            user.setFailedLoginAttempts(0);
            user.setLockedUntil(null);
        }
        
        userRepository.save(user);
        
        // Send reset email
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
        
        return "Password reset link sent to email";
    }
    
    public String resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByResetPasswordToken(request.getToken())
            .orElseThrow(() -> new RuntimeException("Invalid reset token"));
        
        // Check if token has expired
        if (user.getResetPasswordExpiry() == null || LocalDateTime.now().isAfter(user.getResetPasswordExpiry())) {
            throw new RuntimeException("Reset token has expired");
        }
        
        // Reset password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetPasswordToken(null);
        user.setResetPasswordExpiry(null);
        user.setFailedLoginAttempts(0);
        user.setAccountLocked(false);
        user.setLockedUntil(null);
        
        userRepository.save(user);
        
        return "Password reset successfully";
    }
}
