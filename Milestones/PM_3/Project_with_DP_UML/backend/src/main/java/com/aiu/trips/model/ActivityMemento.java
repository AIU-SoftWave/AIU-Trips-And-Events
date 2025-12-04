package com.aiu.trips.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ActivityMemento - snapshot of Activity state for Memento pattern
 * Based on After DP Data_Layer diagram
 */
@Entity
@Table(name = "activity_mementos")
public class ActivityMemento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_id", nullable = false)
    private String activityId;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(name = "activity_date", nullable = false)
    private LocalDateTime activityDate;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @Column(name = "saved_at")
    private LocalDateTime savedAt;

    @PrePersist
    protected void onCreate() {
        if (savedAt == null) {
            savedAt = LocalDateTime.now();
        }
    }

    // Constructors
    public ActivityMemento() {}

    public ActivityMemento(String activityId, String name, String description, LocalDateTime activityDate,
                          String location, Integer capacity, Integer availableSeats) {
        this.activityId = activityId;
        this.name = name;
        this.description = description;
        this.activityDate = activityDate;
        this.location = location;
        this.capacity = capacity;
        this.availableSeats = availableSeats;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
