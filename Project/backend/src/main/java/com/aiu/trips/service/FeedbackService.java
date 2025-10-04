package com.aiu.trips.service;

import com.aiu.trips.model.Event;
import com.aiu.trips.model.EventFeedback;
import com.aiu.trips.model.User;
import com.aiu.trips.model.Booking;
import com.aiu.trips.repository.EventFeedbackRepository;
import com.aiu.trips.repository.EventRepository;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private EventFeedbackRepository feedbackRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookingRepository bookingRepository;

    public EventFeedback submitFeedback(Long eventId, EventFeedback feedback, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));

        // Check if user has attended the event
        boolean hasAttended = bookingRepository.existsByUser_IdAndEvent_IdAndStatus(
            user.getId(), eventId, "ATTENDED"
        );
        
        if (!hasAttended) {
            throw new RuntimeException("You can only provide feedback for events you have attended");
        }

        feedback.setEvent(event);
        feedback.setUser(user);

        return feedbackRepository.save(feedback);
    }

    public List<EventFeedback> getEventFeedback(Long eventId) {
        return feedbackRepository.findByEventId(eventId);
    }
}
