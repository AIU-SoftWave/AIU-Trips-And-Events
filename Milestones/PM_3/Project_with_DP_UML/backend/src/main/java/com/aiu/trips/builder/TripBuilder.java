package com.aiu.trips.builder;

import com.aiu.trips.dto.ActivityDTO;
import com.aiu.trips.enums.ActivityType;
import com.aiu.trips.factory.IModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Concrete Builder for Trip construction
 * Implements step-by-step building of Trip DTOs
 */
@Component
public class TripBuilder implements IActivityBuilder {
    
    @Autowired
    private IModelFactory modelFactory;
    
    private ActivityDTO activity;
    
    public TripBuilder() {
        reset();
    }
    
    @Override
    public void reset() {
        this.activity = new ActivityDTO();
        this.activity.setType(ActivityType.TRIP);
    }
    
    @Override
    public void setBasicInfo(ActivityDTO data) {
        this.activity.setName(data.getName());
        this.activity.setDescription(data.getDescription());
        this.activity.setCategory(data.getCategory());
    }
    
    @Override
    public void setSchedule(LocalDateTime date, String location) {
        this.activity.setActivityDate(date);
        this.activity.setLocation(location);
    }
    
    @Override
    public void setCapacity(Integer capacity) {
        this.activity.setCapacity(capacity);
        this.activity.setAvailableSeats(capacity);
    }
    
    @Override
    public void setPricing(BigDecimal price) {
        this.activity.setPrice(price);
    }
    
    @Override
    public void setOrganizer(Long organizerId) {
        this.activity.setOrganizerId(organizerId);
    }
    
    @Override
    public void setDetails() {
        // Trip-specific details would be set here
        // Destination, duration, transport mode
    }
    
    /**
     * Sets trip-specific fields
     * @param destination Trip destination
     * @param durationDays Trip duration
     * @param transportMode Mode of transport
     */
    public void setTripDetails(String destination, Integer durationDays, String transportMode) {
        this.activity.setDestination(destination);
        this.activity.setDurationDays(durationDays);
        this.activity.setTransportMode(transportMode);
    }
    
    @Override
    public ActivityDTO getResult() {
        ActivityDTO result = this.activity;
        reset(); // Reset for next build
        return result;
    }
}
