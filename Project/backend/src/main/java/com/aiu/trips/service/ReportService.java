package com.aiu.trips.service;

import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.EventFeedback;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.EventRepository;
import com.aiu.trips.repository.EventFeedbackRepository;
import com.aiu.trips.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private EventFeedbackRepository feedbackRepository;
    
    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getEventReport(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));

        List<Booking> bookings = bookingRepository.findByEvent_Id(eventId);
        List<Booking> confirmedBookings = bookings.stream()
            .filter(b -> "CONFIRMED".equals(b.getStatus()))
            .collect(Collectors.toList());
        List<Booking> attendedBookings = bookings.stream()
            .filter(b -> "ATTENDED".equals(b.getStatus()))
            .collect(Collectors.toList());

        Map<String, Object> report = new HashMap<>();
        report.put("eventId", event.getId());
        report.put("eventTitle", event.getTitle());
        report.put("eventType", event.getType());
        report.put("eventCategory", event.getCategory());
        report.put("totalCapacity", event.getCapacity());
        report.put("availableSeats", event.getAvailableSeats());
        report.put("bookedSeats", event.getCapacity() - event.getAvailableSeats());
        report.put("totalParticipants", bookings.size());
        report.put("confirmedParticipants", confirmedBookings.size());
        report.put("attendedParticipants", attendedBookings.size());
        report.put("attendanceRate", confirmedBookings.size() > 0 
            ? (attendedBookings.size() * 100.0 / confirmedBookings.size()) : 0);
        report.put("totalRevenue", bookings.stream()
            .filter(b -> "COMPLETED".equals(b.getPaymentStatus()))
            .mapToDouble(Booking::getAmountPaid)
            .sum());
        report.put("cancelledBookings", bookings.stream()
            .filter(b -> "CANCELLED".equals(b.getStatus()))
            .count());
        report.put("pendingPayments", bookings.stream()
            .filter(b -> "PENDING_PAYMENT".equals(b.getStatus()))
            .count());
        
        // Add feedback statistics
        Double avgRating = feedbackRepository.getAverageRatingByEventId(eventId);
        List<EventFeedback> feedbacks = feedbackRepository.findByEventId(eventId);
        report.put("averageRating", avgRating != null ? avgRating : 0.0);
        report.put("totalFeedbacks", feedbacks.size());
        report.put("feedbacks", feedbacks);

        return report;
    }

    public Map<String, Object> getOverallReport() {
        List<Event> events = eventRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();

        Map<String, Object> report = new HashMap<>();
        report.put("totalEvents", events.size());
        report.put("totalBookings", bookings.size());
        report.put("totalRevenue", bookings.stream()
            .filter(b -> "COMPLETED".equals(b.getPaymentStatus()))
            .mapToDouble(Booking::getAmountPaid)
            .sum());
        report.put("activeEvents", events.stream()
            .filter(e -> "ACTIVE".equals(e.getStatus()))
            .count());
        report.put("completedEvents", events.stream()
            .filter(e -> "COMPLETED".equals(e.getStatus()))
            .count());
        report.put("cancelledEvents", events.stream()
            .filter(e -> "CANCELLED".equals(e.getStatus()))
            .count());
        report.put("totalUsers", userRepository.count());
        
        // Category analytics
        Map<String, Long> categoryStats = events.stream()
            .filter(e -> e.getCategory() != null)
            .collect(Collectors.groupingBy(Event::getCategory, Collectors.counting()));
        report.put("eventsByCategory", categoryStats);
        
        // Most popular categories
        report.put("mostPopularCategory", categoryStats.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("N/A"));

        return report;
    }
    
    public Map<String, Object> getOrganizerPerformance(Long organizerId) {
        List<Event> organizerEvents = eventRepository.findByCreatedBy_Id(organizerId);
        
        Map<String, Object> performance = new HashMap<>();
        performance.put("totalEventsCreated", organizerEvents.size());
        performance.put("activeEvents", organizerEvents.stream()
            .filter(e -> "ACTIVE".equals(e.getStatus()))
            .count());
        performance.put("completedEvents", organizerEvents.stream()
            .filter(e -> "COMPLETED".equals(e.getStatus()))
            .count());
        
        // Calculate total revenue from organizer's events
        double totalRevenue = 0;
        int totalParticipants = 0;
        for (Event event : organizerEvents) {
            List<Booking> eventBookings = bookingRepository.findByEvent_Id(event.getId());
            totalRevenue += eventBookings.stream()
                .filter(b -> "COMPLETED".equals(b.getPaymentStatus()))
                .mapToDouble(Booking::getAmountPaid)
                .sum();
            totalParticipants += eventBookings.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()) || "ATTENDED".equals(b.getStatus()))
                .count();
        }
        
        performance.put("totalRevenue", totalRevenue);
        performance.put("totalParticipants", totalParticipants);
        performance.put("averageParticipantsPerEvent", organizerEvents.size() > 0 
            ? (totalParticipants * 1.0 / organizerEvents.size()) : 0);
        
        return performance;
    }
    
    public Map<String, Object> getAttendanceReport(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        List<Booking> bookings = bookingRepository.findByEvent_Id(eventId);
        
        Map<String, Object> report = new HashMap<>();
        report.put("eventTitle", event.getTitle());
        report.put("totalExpectedAttendees", bookings.stream()
            .filter(b -> "CONFIRMED".equals(b.getStatus()))
            .count());
        report.put("actualAttendees", bookings.stream()
            .filter(b -> "ATTENDED".equals(b.getStatus()))
            .count());
        report.put("noShows", bookings.stream()
            .filter(b -> "CONFIRMED".equals(b.getStatus()))
            .count() - bookings.stream()
            .filter(b -> "ATTENDED".equals(b.getStatus()))
            .count());
        
        List<Map<String, Object>> attendeeList = bookings.stream()
            .filter(b -> "ATTENDED".equals(b.getStatus()))
            .map(b -> {
                Map<String, Object> attendee = new HashMap<>();
                attendee.put("name", b.getUser().getFullName());
                attendee.put("email", b.getUser().getEmail());
                attendee.put("attendedAt", b.getAttendedAt());
                return attendee;
            })
            .collect(Collectors.toList());
        
        report.put("attendees", attendeeList);
        
        return report;
    }
}
