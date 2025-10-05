package com.aiu.trips.service;

import com.aiu.trips.model.Event;
import com.aiu.trips.model.Booking;
import com.aiu.trips.model.User;
import com.aiu.trips.enums.BookingStatus;
import com.aiu.trips.enums.EventStatus;
import com.aiu.trips.repository.EventRepository;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Map<String, Object> getSystemStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // Total users
        long totalUsers = userRepository.count();
        stats.put("totalUsers", totalUsers);
        
        // Total events
        long totalEvents = eventRepository.count();
        stats.put("totalEvents", totalEvents);
        
        // Active events
        long activeEvents = eventRepository.findByStatus(EventStatus.ACTIVE).size();
        stats.put("activeEvents", activeEvents);
        
        // Total bookings
        long totalBookings = bookingRepository.count();
        stats.put("totalBookings", totalBookings);
        
        // Confirmed bookings
        long confirmedBookings = bookingRepository.findByStatus(BookingStatus.CONFIRMED).size();
        stats.put("confirmedBookings", confirmedBookings);
        
        // Total revenue
        Double totalRevenue = calculateTotalRevenue();
        stats.put("totalRevenue", totalRevenue);
        
        return stats;
    }
    
    public Map<String, Object> getEventStatistics(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        Map<String, Object> stats = new HashMap<>();
        
        // Event details
        stats.put("eventId", event.getId());
        stats.put("eventTitle", event.getTitle());
        stats.put("capacity", event.getCapacity());
        stats.put("availableSeats", event.getAvailableSeats());
        
        // Bookings for this event
        List<Booking> bookings = bookingRepository.findByEvent(event);
        stats.put("totalBookings", bookings.size());
        
        // Confirmed bookings
        long confirmedBookings = bookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
            .count();
        stats.put("confirmedBookings", confirmedBookings);
        
        // Revenue for this event
        Double eventRevenue = bookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
            .mapToDouble(b -> b.getAmountPaid() != null ? b.getAmountPaid() : 0.0)
            .sum();
        stats.put("revenue", eventRevenue);
        
        // Capacity utilization
        double utilizationRate = (event.getCapacity() - event.getAvailableSeats()) * 100.0 / event.getCapacity();
        stats.put("capacityUtilization", Math.round(utilizationRate * 100.0) / 100.0);
        
        return stats;
    }
    
    public Map<String, Object> getUserEngagement() {
        Map<String, Object> engagement = new HashMap<>();
        
        List<User> users = userRepository.findAll();
        engagement.put("totalUsers", users.size());
        
        // Users with bookings
        long activeUsers = bookingRepository.findAll().stream()
            .map(b -> b.getUser().getId())
            .distinct()
            .count();
        engagement.put("activeUsers", activeUsers);
        
        // Average bookings per user
        long totalBookings = bookingRepository.count();
        double avgBookingsPerUser = users.size() > 0 ? (double) totalBookings / users.size() : 0;
        engagement.put("averageBookingsPerUser", Math.round(avgBookingsPerUser * 100.0) / 100.0);
        
        return engagement;
    }
    
    public Map<String, Object> getRevenueAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Object> analytics = new HashMap<>();
        
        List<Booking> bookings = bookingRepository.findByBookingDateBetween(startDate, endDate);
        
        // Total revenue in period
        Double totalRevenue = bookings.stream()
            .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
            .mapToDouble(b -> b.getAmountPaid() != null ? b.getAmountPaid() : 0.0)
            .sum();
        analytics.put("totalRevenue", totalRevenue);
        
        // Number of transactions
        analytics.put("numberOfTransactions", bookings.size());
        
        // Average transaction value
        double avgTransaction = bookings.size() > 0 ? totalRevenue / bookings.size() : 0;
        analytics.put("averageTransactionValue", Math.round(avgTransaction * 100.0) / 100.0);
        
        return analytics;
    }
    
    public Map<String, Long> getEventCategoryDistribution() {
        Map<String, Long> distribution = new HashMap<>();
        
        List<Event> events = eventRepository.findAll();
        events.forEach(event -> {
            String category = event.getCategory() != null ? event.getCategory() : "Uncategorized";
            distribution.put(category, distribution.getOrDefault(category, 0L) + 1);
        });
        
        return distribution;
    }
    
    private Double calculateTotalRevenue() {
        return bookingRepository.findByStatus(BookingStatus.CONFIRMED).stream()
            .mapToDouble(b -> b.getAmountPaid() != null ? b.getAmountPaid() : 0.0)
            .sum();
    }
}
