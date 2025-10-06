package com.aiu.trips.controller;

import com.aiu.trips.dto.RegisterRequest;
import com.aiu.trips.model.User;
import com.aiu.trips.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * Verify email with token
     */
    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        boolean verified = userService.verifyEmail(token);
        Map<String, Object> response = new HashMap<>();
        response.put("success", verified);
        response.put("message", "Email verified successfully");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Initiate password reset
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.initiatePasswordReset(email);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Password reset email sent");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Reset password with token
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("password");
        
        boolean reset = userService.resetPassword(token, newPassword);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", reset);
        response.put("message", "Password reset successfully");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get current user profile
     */
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getCurrentUser(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
    
    /**
     * Update user profile
     */
    @PutMapping("/profile/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody RegisterRequest updateData) {
        User updatedUser = userService.updateUser(id, updateData);
        return ResponseEntity.ok(updatedUser);
    }
    
    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
