package com.aiu.trips.builder;

import com.aiu.trips.dto.ActivityDTO;
import com.aiu.trips.enums.ActivityType;
import com.aiu.trips.factory.IModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Concrete Builder for Event construction
 * Implements step-by-step building of Event DTOs
 */
@Component
public class EventBuilder implements IActivityBuilder {
    
    @Autowired
    private IModelFactory modelFactory;
    
    private ActivityDTO activity;
    
    public EventBuilder() {
        reset();
    }
    
    @Override
    public void reset() {
        this.activity = new ActivityDTO();
        this.activity.setType(ActivityType.EVENT);
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
        // Event-specific details would be set here
        // Topic, venue, speakers, agenda
    }
    
    /**
     * Sets event-specific fields
     * @param topic Event topic
     * @param venue Event venue
     */
    public void setEventDetails(String topic, String venue) {
        this.activity.setTopic(topic);
        this.activity.setVenue(venue);
    }
    
    @Override
    public ActivityDTO getResult() {
        ActivityDTO result = this.activity;
        reset(); // Reset for next build
        return result;
    }
}
