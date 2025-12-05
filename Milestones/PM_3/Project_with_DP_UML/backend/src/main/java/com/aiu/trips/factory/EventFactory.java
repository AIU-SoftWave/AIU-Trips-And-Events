package com.aiu.trips.factory;

import com.aiu.trips.builder.EventBuilder;
import com.aiu.trips.builder.IActivityDirector;
import com.aiu.trips.dto.ActivityDTO;
import com.aiu.trips.enums.ActivityCategory;
import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.enums.ActivityType;
import com.aiu.trips.model.Activity;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Concrete Factory for creating Event objects
 * Implements Abstract Factory pattern
 */
@Component
public class EventFactory implements IActivityFactory {
    
    @Autowired
    private IActivityDirector director;
    
    @Autowired
    private EventBuilder builder;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Activity createActivity(ActivityDTO data) {
        // Use director and builder to construct
        director.setBuilder(builder);
        ActivityDTO builtData = director.makeNormalEvent(data);
        
        // Convert DTO to Entity
        Event event = new Event();
        event.setName(builtData.getName());
        event.setDescription(builtData.getDescription());
        event.setActivityDate(builtData.getActivityDate());
        event.setLocation(builtData.getLocation());
        event.setCapacity(builtData.getCapacity());
        event.setAvailableSeats(builtData.getAvailableSeats());
        event.setPrice(builtData.getPrice());
        event.setCategory(builtData.getCategory() != null ? builtData.getCategory() : ActivityCategory.SEMINAR);
        event.setStatus(builtData.getStatus() != null ? builtData.getStatus() : ActivityStatus.UPCOMING);
        
        // Set organizer if provided
        if (builtData.getOrganizerId() != null) {
            userRepository.findById(builtData.getOrganizerId())
                .ifPresent(event::setOrganizer);
        }
        
        // Event-specific fields
        event.setTopic(builtData.getTopic());
        event.setVenue(builtData.getVenue());
        event.setAgenda(builtData.getAgenda());
        if (builtData.getSpeakers() != null) {
            event.setSpeakers(builtData.getSpeakers());
        }
        
        return event;
    }
    
    @Override
    public Activity clonePrototype(Activity activity) {
        if (!(activity instanceof Event)) {
            throw new IllegalArgumentException("Activity must be an Event");
        }
        
        Event source = (Event) activity;
        Event clone = new Event();
        
        // Copy all fields
        clone.setName(source.getName() + " (Copy)");
        clone.setDescription(source.getDescription());
        clone.setActivityDate(source.getActivityDate());
        clone.setLocation(source.getLocation());
        clone.setCapacity(source.getCapacity());
        clone.setAvailableSeats(source.getCapacity());
        clone.setPrice(source.getPrice());
        clone.setCategory(source.getCategory());
        clone.setStatus(ActivityStatus.UPCOMING);
        clone.setOrganizer(source.getOrganizer());
        
        // Event-specific
        clone.setTopic(source.getTopic());
        clone.setVenue(source.getVenue());
        clone.setAgenda(source.getAgenda());
        if (source.getSpeakers() != null) {
            clone.setSpeakers(Arrays.asList(source.getSpeakers().toArray(new String[0])));
        }
        
        return clone;
    }
}
