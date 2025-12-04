package com.aiu.trips.builder;

import com.aiu.trips.enums.EventType;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Builder Pattern - Concrete builder for Event objects
 */
@Component
public class EventBuilder implements IActivityBuilder {
    
    private Event event;
    
    public EventBuilder() {
        this.reset();
    }
    
    @Override
    public void reset() {
        this.event = new Event();
        this.event.setType(EventType.EVENT);
    }
    
    @Override
    public IActivityBuilder setTitle(String title) {
        this.event.setTitle(title);
        return this;
    }
    
    @Override
    public IActivityBuilder setDescription(String description) {
        this.event.setDescription(description);
        return this;
    }
    
    @Override
    public IActivityBuilder setType(EventType type) {
        // Type is always EVENT for EventBuilder - parameter ignored for interface compatibility
        this.event.setType(EventType.EVENT);
        return this;
    }
    
    @Override
    public IActivityBuilder setStartDate(LocalDateTime startDate) {
        this.event.setStartDate(startDate);
        return this;
    }
    
    @Override
    public IActivityBuilder setEndDate(LocalDateTime endDate) {
        this.event.setEndDate(endDate);
        return this;
    }
    
    @Override
    public IActivityBuilder setLocation(String location) {
        this.event.setLocation(location);
        return this;
    }
    
    @Override
    public IActivityBuilder setPrice(Double price) {
        this.event.setPrice(price);
        return this;
    }
    
    @Override
    public IActivityBuilder setCapacity(Integer capacity) {
        this.event.setCapacity(capacity);
        return this;
    }
    
    @Override
    public IActivityBuilder setImageUrl(String imageUrl) {
        this.event.setImageUrl(imageUrl);
        return this;
    }
    
    @Override
    public IActivityBuilder setCreatedBy(User user) {
        this.event.setCreatedBy(user);
        return this;
    }
    
    @Override
    public Event build() {
        Event result = this.event;
        this.reset();
        return result;
    }
}
