package com.aiu.trips.service;

import com.aiu.trips.constants.AppConstants;
import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.enums.ActivityType;
import com.aiu.trips.exception.ResourceNotFoundException;
import com.aiu.trips.model.Activity;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.EventRepository;
import com.aiu.trips.repository.UserRepository;
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

    public Activity createEvent(Event event, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userEmail));
        
        event.setCreatedBy(user);
        Activity savedEvent = eventRepository.save(event);
        
        // Notify all users about new event
        notificationService.notifyAllUsers(
            "New event available: " + event.getName(),
            "INFO"
        );
        
        return savedEvent;
    }

    public Activity updateEvent(Long id, Event eventDetails) {
        Activity activity = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EVENT_NOT_FOUND + id));
        
        if (activity instanceof Event) {
            Event event = (Event) activity;
            event.setName(eventDetails.getName());
            event.setDescription(eventDetails.getDescription());
            event.setActivityDate(eventDetails.getActivityDate());
            event.setLocation(eventDetails.getLocation());
            event.setPrice(eventDetails.getPrice());
            event.setCapacity(eventDetails.getCapacity());
            event.setCategory(eventDetails.getCategory());
            event.setTopic(eventDetails.getTopic());
            event.setVenue(eventDetails.getVenue());
            
            Activity updatedEvent = eventRepository.save(event);
            
            // Notify users about update
            notificationService.notifyEventParticipants(
                id,
                "Event updated: " + event.getName(),
                "INFO"
            );
            
            return updatedEvent;
        }
        
        return activity;
    }

    public void deleteEvent(Long id) {
        Activity activity = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EVENT_NOT_FOUND + id));
        
        activity.setStatus(ActivityStatus.CANCELLED);
        eventRepository.save(activity);
        
        // Notify participants about cancellation
        notificationService.notifyEventParticipants(
            id,
            "Event cancelled: " + activity.getName(),
            "WARNING"
        );
    }

    public List<Activity> getAllEvents() {
        return eventRepository.findAll();
    }

    public Activity getEventById(Long id) {
        return eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EVENT_NOT_FOUND + id));
    }

    public List<Activity> getEventsByType(ActivityType type) {
        return eventRepository.findByType(type);
    }

    public List<Activity> getUpcomingEvents() {
        return eventRepository.findByActivityDateAfter(LocalDateTime.now());
    }

    public List<Activity> getEventsByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userEmail));
        return eventRepository.findByCreatedBy_Id(user.getId());
    }
}
