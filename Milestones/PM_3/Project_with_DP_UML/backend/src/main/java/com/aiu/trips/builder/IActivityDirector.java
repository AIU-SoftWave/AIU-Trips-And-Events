package com.aiu.trips.builder;

import com.aiu.trips.dto.ActivityDTO;

/**
 * Director Pattern for Activity Builder
 * Orchestrates the building process
 */
public interface IActivityDirector {
    /**
     * Sets the builder to use
     * @param builder Activity builder
     */
    void setBuilder(IActivityBuilder builder);
    
    /**
     * Constructs activity from data
     * @param data Activity data
     * @return Constructed ActivityDTO
     */
    ActivityDTO constructFrom(ActivityDTO data);
    
    /**
     * Makes a normal event
     * @param data Event data
     * @return Constructed event DTO
     */
    ActivityDTO makeNormalEvent(ActivityDTO data);
    
    /**
     * Makes a normal trip
     * @param data Trip data
     * @return Constructed trip DTO
     */
    ActivityDTO makeNormalTrip(ActivityDTO data);
    
    /**
     * Makes a high capacity event
     * @param data Event data
     * @param capacity Capacity
     * @return Constructed event DTO
     */
    ActivityDTO makeHighCapacityEvent(ActivityDTO data, Integer capacity);
    
    /**
     * Makes a high capacity trip
     * @param data Trip data
     * @param capacity Capacity
     * @return Constructed trip DTO
     */
    ActivityDTO makeHighCapacityTrip(ActivityDTO data, Integer capacity);
}
