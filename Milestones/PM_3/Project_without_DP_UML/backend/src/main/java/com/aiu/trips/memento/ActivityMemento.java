package com.aiu.trips.memento;

import com.aiu.trips.enums.EventStatus;
import com.aiu.trips.enums.EventType;

import java.time.LocalDateTime;

/**
 * Memento Pattern - Memento for Activity state
 * Stores the state of an Activity (Event/Trip) at a point in time
 */
public class ActivityMemento {
    
    private final Long id;
    private final String title;
    private final String description;
    private final EventType type;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String location;
    private final Double price;
    private final Integer capacity;
    private final Integer availableSeats;
    private final EventStatus status;
    private final LocalDateTime savedAt;
    
    public ActivityMemento(Long id, String title, String description, EventType type,
                          LocalDateTime startDate, LocalDateTime endDate, String location,
                          Double price, Integer capacity, Integer availableSeats,
                          EventStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.price = price;
        this.capacity = capacity;
        this.availableSeats = availableSeats;
        this.status = status;
        this.savedAt = LocalDateTime.now();
    }
    
    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public EventType getType() { return type; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public String getLocation() { return location; }
    public Double getPrice() { return price; }
    public Integer getCapacity() { return capacity; }
    public Integer getAvailableSeats() { return availableSeats; }
    public EventStatus getStatus() { return status; }
    public LocalDateTime getSavedAt() { return savedAt; }
}
