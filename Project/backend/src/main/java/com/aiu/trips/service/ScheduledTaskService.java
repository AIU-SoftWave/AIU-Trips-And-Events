package com.aiu.trips.service;

import com.aiu.trips.model.Event;
import com.aiu.trips.model.Booking;
import com.aiu.trips.enums.EventStatus;
import com.aiu.trips.repository.EventRepository;
import com.aiu.trips.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Scheduled tasks service for automated system operations
 * - Send event reminders 24 hours before event
 * - Auto-update event status
 * - Clean up expired tokens
 */
@Service
public class ScheduledTaskService {
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    /**
     * Send reminders for events happening in 24 hours
     * Runs every hour
     */
    @Scheduled(cron = "0 0 * * * *") // Every hour at minute 0
    @Transactional
    public void sendEventReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusHours(24);
        LocalDateTime tomorrowPlusOne = tomorrow.plusHours(1);
        
        // Get events starting in the next 24 hours
        List<Event> upcomingEvents = eventRepository.findByStartDateBetween(tomorrow, tomorrowPlusOne);
        
        for (Event event : upcomingEvents) {
            // Get all bookings for this event
            List<Booking> bookings = bookingRepository.findByEvent_Id(event.getId());
            
            for (Booking booking : bookings) {
                String message = String.format(
                    "Reminder: %s starts tomorrow at %s. Location: %s. Your booking code: %s",
                    event.getTitle(),
                    event.getStartDate().toLocalTime(),
                    event.getLocation(),
                    booking.getBookingCode()
                );
                
                notificationService.notifyUser(
                    booking.getUser().getId(),
                    message,
                    "REMINDER"
                );
                
                // Also send email
                notificationService.sendEmail(
                    booking.getUser().getEmail(),
                    "Event Reminder: " + event.getTitle(),
                    message
                );
            }
            
            System.out.println("Sent reminders for event: " + event.getTitle() + " to " + bookings.size() + " participants");
        }
    }
    
    /**
     * Update event status based on current date/time
     * Runs every 30 minutes
     */
    @Scheduled(cron = "0 */30 * * * *") // Every 30 minutes
    @Transactional
    public void updateEventStatuses() {
        LocalDateTime now = LocalDateTime.now();
        
        // Mark events as ONGOING if they've started but not ended
        List<Event> publishedEvents = eventRepository.findByStatus(EventStatus.PUBLISHED);
        for (Event event : publishedEvents) {
            if (event.getStartDate().isBefore(now)) {
                if (event.getEndDate() == null || event.getEndDate().isAfter(now)) {
                    event.setStatus(EventStatus.ONGOING);
                    eventRepository.save(event);
                }
            }
        }
        
        // Mark ONGOING events as COMPLETED if they've ended
        List<Event> ongoingEvents = eventRepository.findByStatus(EventStatus.ONGOING);
        for (Event event : ongoingEvents) {
            if (event.getEndDate() != null && event.getEndDate().isBefore(now)) {
                event.setStatus(EventStatus.COMPLETED);
                eventRepository.save(event);
            }
        }
        
        // Also check ACTIVE events
        List<Event> activeEvents = eventRepository.findByStatus(EventStatus.ACTIVE);
        for (Event event : activeEvents) {
            if (event.getStartDate().isBefore(now)) {
                if (event.getEndDate() == null || event.getEndDate().isAfter(now)) {
                    event.setStatus(EventStatus.ONGOING);
                } else if (event.getEndDate().isBefore(now)) {
                    event.setStatus(EventStatus.COMPLETED);
                }
                eventRepository.save(event);
            }
        }
    }
    
    /**
     * Send notification when new event is published
     */
    public void notifyNewEvent(Event event) {
        String message = String.format(
            "New %s: %s on %s. Price: $%.2f. Book now!",
            event.getType().toString().toLowerCase(),
            event.getTitle(),
            event.getStartDate().toLocalDate(),
            event.getPrice()
        );
        
        notificationService.notifyAllUsers(message, "NEW_EVENT");
    }
    
    /**
     * Send notification when event is updated
     */
    public void notifyEventUpdate(Event event, String changes) {
        String message = String.format(
            "Event Updated: %s. Changes: %s",
            event.getTitle(),
            changes
        );
        
        notificationService.notifyEventParticipants(event.getId(), message, "EVENT_UPDATE");
    }
    
    /**
     * Send notification when event is cancelled
     */
    public void notifyEventCancellation(Event event) {
        String message = String.format(
            "Event Cancelled: %s scheduled for %s has been cancelled. Refunds will be processed.",
            event.getTitle(),
            event.getStartDate().toLocalDate()
        );
        
        notificationService.notifyEventParticipants(event.getId(), message, "EVENT_CANCELLED");
    }
}
