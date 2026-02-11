package com.aiu.trips.builder;

import com.aiu.trips.enums.EventType;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Builder Pattern - Concrete builder for Trip objects
 */
@Component
public class TripBuilder implements IActivityBuilder {
    
    private Event trip;
    
    public TripBuilder() {
        this.reset();
    }
    
    @Override
    public void reset() {
        this.trip = new Event();
        this.trip.setType(EventType.TRIP);
    }
    
    @Override
    public IActivityBuilder setTitle(String title) {
        this.trip.setTitle(title);
        return this;
    }
    
    @Override
    public IActivityBuilder setDescription(String description) {
        this.trip.setDescription(description);
        return this;
    }
    
    @Override
    public IActivityBuilder setType(EventType type) {
        // Type is always TRIP for TripBuilder - parameter ignored for interface compatibility
        this.trip.setType(EventType.TRIP);
        return this;
    }
    
    @Override
    public IActivityBuilder setStartDate(LocalDateTime startDate) {
        this.trip.setStartDate(startDate);
        return this;
    }
    
    @Override
    public IActivityBuilder setEndDate(LocalDateTime endDate) {
        this.trip.setEndDate(endDate);
        return this;
    }
    
    @Override
    public IActivityBuilder setLocation(String location) {
        this.trip.setLocation(location);
        return this;
    }
    
    @Override
    public IActivityBuilder setPrice(Double price) {
        this.trip.setPrice(price);
        return this;
    }
    
    @Override
    public IActivityBuilder setCapacity(Integer capacity) {
        this.trip.setCapacity(capacity);
        return this;
    }
    
    @Override
    public IActivityBuilder setImageUrl(String imageUrl) {
        this.trip.setImageUrl(imageUrl);
        return this;
    }
    
    @Override
    public IActivityBuilder setCreatedBy(User user) {
        this.trip.setCreatedBy(user);
        return this;
    }
    
    @Override
    public Event build() {
        Event result = this.trip;
        this.reset();
        return result;
    }
}
