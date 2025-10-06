package com.aiu.trips.service;

import com.aiu.trips.model.User;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.Booking;
import com.aiu.trips.enums.UserRole;
import com.aiu.trips.enums.EventStatus;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.repository.EventRepository;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class AdminService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserService userService;
    
    /**
     * Get all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Get users by role
     */
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
    
    /**
     * Assign role to user
     */
    @Transactional
    public User assignRole(Long userId, UserRole role) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        user.setRole(role);
        return userRepository.save(user);
    }
    
    /**
     * Activate/Deactivate user
     */
    @Transactional
    public User toggleUserStatus(Long userId, boolean active) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        user.setIsActive(active);
        return userRepository.save(user);
    }
    
    /**
     * Lock user account
     */
    @Transactional
    public void lockUserAccount(Long userId) {
        userService.lockAccount(userId);
    }
    
    /**
     * Unlock user account
     */
    @Transactional
    public void unlockUserAccount(Long userId) {
        userService.unlockAccount(userId);
    }
    
    /**
     * Delete user
     */
    @Transactional
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }
    
    /**
     * Get system statistics
     */
    public Map<String, Object> getSystemStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // User statistics
        long totalUsers = userRepository.count();
        long totalStudents = userRepository.findByRole(UserRole.STUDENT).size();
        long totalOrganizers = userRepository.findByRole(UserRole.ORGANIZER).size();
        long totalAdmins = userRepository.findByRole(UserRole.ADMIN).size();
        
        stats.put("totalUsers", totalUsers);
        stats.put("totalStudents", totalStudents);
        stats.put("totalOrganizers", totalOrganizers);
        stats.put("totalAdmins", totalAdmins);
        
        // Event statistics
        long totalEvents = eventRepository.count();
        long activeEvents = eventRepository.findByStatus(EventStatus.ACTIVE).size();
        long completedEvents = eventRepository.findByStatus(EventStatus.COMPLETED).size();
        long cancelledEvents = eventRepository.findByStatus(EventStatus.CANCELLED).size();
        
        stats.put("totalEvents", totalEvents);
        stats.put("activeEvents", activeEvents);
        stats.put("completedEvents", completedEvents);
        stats.put("cancelledEvents", cancelledEvents);
        
        // Booking statistics
        long totalBookings = bookingRepository.count();
        
        stats.put("totalBookings", totalBookings);
        
        return stats;
    }
    
    /**
     * Get user activity logs
     */
    public List<User> searchUsers(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return userRepository.findAll();
        }
        
        return userRepository.findByEmailContainingOrFullNameContaining(searchTerm, searchTerm);
    }
    
    /**
     * Approve event (change from DRAFT to PUBLISHED)
     */
    @Transactional
    public Event approveEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        
        if (event.getStatus() == EventStatus.DRAFT) {
            event.setStatus(EventStatus.PUBLISHED);
            return eventRepository.save(event);
        }
        
        throw new IllegalStateException("Event is not in draft status");
    }
    
    /**
     * Reject event
     */
    @Transactional
    public void rejectEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        
        event.setStatus(EventStatus.CANCELLED);
        eventRepository.save(event);
    }
    
    /**
     * Get all pending events (DRAFT status)
     */
    public List<Event> getPendingEvents() {
        return eventRepository.findByStatus(EventStatus.DRAFT);
    }
}
