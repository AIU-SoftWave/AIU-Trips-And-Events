package com.aiu.trips.controller;

import com.aiu.trips.model.User;
import com.aiu.trips.model.Event;
import com.aiu.trips.enums.UserRole;
import com.aiu.trips.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    /**
     * Get all users
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    /**
     * Get users by role
     */
    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable UserRole role) {
        List<User> users = adminService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
    
    /**
     * Search users
     */
    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = adminService.searchUsers(query);
        return ResponseEntity.ok(users);
    }
    
    /**
     * Assign role to user
     */
    @PutMapping("/users/{userId}/role")
    public ResponseEntity<User> assignRole(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        UserRole role = UserRole.valueOf(request.get("role"));
        User user = adminService.assignRole(userId, role);
        return ResponseEntity.ok(user);
    }
    
    /**
     * Toggle user active status
     */
    @PutMapping("/users/{userId}/status")
    public ResponseEntity<User> toggleUserStatus(
            @PathVariable Long userId,
            @RequestBody Map<String, Boolean> request) {
        boolean active = request.get("active");
        User user = adminService.toggleUserStatus(userId, active);
        return ResponseEntity.ok(user);
    }
    
    /**
     * Lock user account
     */
    @PostMapping("/users/{userId}/lock")
    public ResponseEntity<Map<String, String>> lockUser(@PathVariable Long userId) {
        adminService.lockUserAccount(userId);
        return ResponseEntity.ok(Map.of("message", "User account locked successfully"));
    }
    
    /**
     * Unlock user account
     */
    @PostMapping("/users/{userId}/unlock")
    public ResponseEntity<Map<String, String>> unlockUser(@PathVariable Long userId) {
        adminService.unlockUserAccount(userId);
        return ResponseEntity.ok(Map.of("message", "User account unlocked successfully"));
    }
    
    /**
     * Delete user
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
    
    /**
     * Get system statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getSystemStatistics() {
        Map<String, Object> stats = adminService.getSystemStatistics();
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Get pending events for approval
     */
    @GetMapping("/events/pending")
    public ResponseEntity<List<Event>> getPendingEvents() {
        List<Event> events = adminService.getPendingEvents();
        return ResponseEntity.ok(events);
    }
    
    /**
     * Approve event
     */
    @PostMapping("/events/{eventId}/approve")
    public ResponseEntity<Event> approveEvent(@PathVariable Long eventId) {
        Event event = adminService.approveEvent(eventId);
        return ResponseEntity.ok(event);
    }
    
    /**
     * Reject event
     */
    @PostMapping("/events/{eventId}/reject")
    public ResponseEntity<Map<String, String>> rejectEvent(@PathVariable Long eventId) {
        adminService.rejectEvent(eventId);
        return ResponseEntity.ok(Map.of("message", "Event rejected successfully"));
    }
}
