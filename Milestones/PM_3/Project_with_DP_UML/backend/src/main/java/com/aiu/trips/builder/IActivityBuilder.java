package com.aiu.trips.builder;

import com.aiu.trips.dto.ActivityDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Builder Pattern Interface for Activity construction
 * Provides step-by-step construction of Activity objects
 */
public interface IActivityBuilder {
    /**
     * Resets the builder to initial state
     */
    void reset();
    
    /**
     * Sets basic information
     * @param data Activity data
     */
    void setBasicInfo(ActivityDTO data);
    
    /**
     * Sets schedule information
     * @param date Activity date
     * @param location Activity location
     */
    void setSchedule(LocalDateTime date, String location);
    
    /**
     * Sets capacity
     * @param capacity Maximum capacity
     */
    void setCapacity(Integer capacity);
    
    /**
     * Sets pricing
     * @param price Activity price
     */
    void setPricing(BigDecimal price);
    
    /**
     * Sets organizer
     * @param organizerId Organizer user ID
     */
    void setOrganizer(Long organizerId);
    
    /**
     * Sets additional details (event/trip specific)
     */
    void setDetails();
    
    /**
     * Returns the constructed activity DTO
     * @return Constructed ActivityDTO
     */
    ActivityDTO getResult();
}
