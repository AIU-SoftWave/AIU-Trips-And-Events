package com.aiu.trips.service;

import com.aiu.trips.model.Event;
import com.aiu.trips.model.User;
import com.aiu.trips.model.Booking;
import com.aiu.trips.repository.EventRepository;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private BookingRepository bookingRepository;

    public Event createEvent(Event event, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        event.setCreatedBy(user);
        Event savedEvent = eventRepository.save(event);
        
        // Notify all users about new event
        notificationService.notifyAllUsers(
            "New " + event.getType().toLowerCase() + " available: " + event.getTitle(),
            "INFO"
        );
        
        return savedEvent;
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        boolean locationChanged = !event.getLocation().equals(eventDetails.getLocation());
        boolean timeChanged = !event.getStartDate().equals(eventDetails.getStartDate());
        
        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setType(eventDetails.getType());
        event.setCategory(eventDetails.getCategory());
        event.setStartDate(eventDetails.getStartDate());
        event.setStartTime(eventDetails.getStartTime());
        event.setEndDate(eventDetails.getEndDate());
        event.setLocation(eventDetails.getLocation());
        event.setPrice(eventDetails.getPrice());
        event.setCapacity(eventDetails.getCapacity());
        event.setRegistrationDeadline(eventDetails.getRegistrationDeadline());
        event.setImageUrl(eventDetails.getImageUrl());
        
        Event updatedEvent = eventRepository.save(event);
        
        // Notify users about update
        String updateMessage = "Event updated: " + event.getTitle();
        if (locationChanged || timeChanged) {
            updateMessage += " - Important: ";
            if (locationChanged) updateMessage += "Location changed to " + event.getLocation() + ". ";
            if (timeChanged) updateMessage += "Time changed to " + event.getStartDate() + ". ";
        }
        
        notificationService.notifyEventParticipants(id, updateMessage, "INFO");
        
        // Send email notifications to all participants
        List<Booking> bookings = bookingRepository.findByEvent_Id(id);
        for (Booking booking : bookings) {
            if ("CONFIRMED".equals(booking.getStatus())) {
                emailService.sendEventUpdateNotification(
                    booking.getUser().getEmail(),
                    event.getTitle(),
                    updateMessage
                );
            }
        }
        
        return updatedEvent;
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        event.setStatus("CANCELLED");
        eventRepository.save(event);
        
        // Notify participants about cancellation
        notificationService.notifyEventParticipants(
            id,
            "Event cancelled: " + event.getTitle(),
            "WARNING"
        );
        
        // Send email notifications to all participants
        List<Booking> bookings = bookingRepository.findByEvent_Id(id);
        for (Booking booking : bookings) {
            if ("CONFIRMED".equals(booking.getStatus())) {
                emailService.sendEventCancellationNotification(
                    booking.getUser().getEmail(),
                    event.getTitle()
                );
                // Update booking status
                booking.setStatus("CANCELLED");
                booking.setPaymentStatus("REFUNDED");
                bookingRepository.save(booking);
            }
        }
    }
    
    public void sendCustomMessageToParticipants(Long eventId, String message, String userEmail) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        User organizer = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check if user is the organizer
        if (!event.getCreatedBy().getId().equals(organizer.getId())) {
            throw new RuntimeException("Only event organizer can send messages to participants");
        }
        
        List<Booking> bookings = bookingRepository.findByEvent_Id(eventId);
        for (Booking booking : bookings) {
            if ("CONFIRMED".equals(booking.getStatus())) {
                emailService.sendCustomMessage(
                    booking.getUser().getEmail(),
                    event.getTitle(),
                    message,
                    organizer.getFullName()
                );
            }
        }
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public List<Event> getEventsByType(String type) {
        return eventRepository.findByType(type);
    }
    
    public List<Event> getEventsByCategory(String category) {
        return eventRepository.findByCategory(category);
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByStartDateAfter(LocalDateTime.now());
    }

    public List<Event> getEventsByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return eventRepository.findByCreatedBy_Id(user.getId());
    }
}
