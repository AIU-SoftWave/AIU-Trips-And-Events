package com.aiu.trips.builder;

import com.aiu.trips.enums.EventType;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Builder Pattern - Director class for Activity construction
 * Orchestrates the building process using builders
 */
@Component
public class ActivityDirector {
    
    @Autowired
    private EventBuilder eventBuilder;
    
    @Autowired
    private TripBuilder tripBuilder;
    
    public Event constructEvent(String title, String description, LocalDateTime startDate, 
                               String location, Double price, Integer capacity, User createdBy) {
        return eventBuilder
            .setTitle(title)
            .setDescription(description)
            .setStartDate(startDate)
            .setLocation(location)
            .setPrice(price)
            .setCapacity(capacity)
            .setCreatedBy(createdBy)
            .build();
    }
    
    public Event constructTrip(String title, String description, LocalDateTime startDate, 
                              LocalDateTime endDate, String location, Double price, 
                              Integer capacity, User createdBy) {
        return tripBuilder
            .setTitle(title)
            .setDescription(description)
            .setStartDate(startDate)
            .setEndDate(endDate)
            .setLocation(location)
            .setPrice(price)
            .setCapacity(capacity)
            .setCreatedBy(createdBy)
            .build();
    }
}
