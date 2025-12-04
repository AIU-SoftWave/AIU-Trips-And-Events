package com.aiu.trips.builder;

import com.aiu.trips.enums.EventType;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.User;

import java.time.LocalDateTime;

/**
 * Builder Pattern - Interface for building Activity (Event/Trip) objects
 * Provides a fluent interface for constructing complex Activity objects
 */
public interface IActivityBuilder {
    IActivityBuilder setTitle(String title);
    IActivityBuilder setDescription(String description);
    IActivityBuilder setType(EventType type);
    IActivityBuilder setStartDate(LocalDateTime startDate);
    IActivityBuilder setEndDate(LocalDateTime endDate);
    IActivityBuilder setLocation(String location);
    IActivityBuilder setPrice(Double price);
    IActivityBuilder setCapacity(Integer capacity);
    IActivityBuilder setImageUrl(String imageUrl);
    IActivityBuilder setCreatedBy(User user);
    Event build();
    void reset();
}
