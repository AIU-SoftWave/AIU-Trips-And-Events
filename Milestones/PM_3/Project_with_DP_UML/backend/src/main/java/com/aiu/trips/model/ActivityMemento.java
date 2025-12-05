package com.aiu.trips.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Memento Pattern: Snapshot of Activity state
 * Stores historical state for undo/rollback functionality
 */
@Entity
@Table(name = "activity_mementos")
public class ActivityMemento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mementoId;
    
    @Column(nullable = false)
    private Long activityId;
    
    @Column
    private String name;
    
    @Column(length = 2000)
    private String description;
    
    @Column
    private LocalDateTime activityDate;
    
    @Column
    private String location;
    
    @Column
    private Integer capacity;
    
    @Column
    private Integer availableSeats;
    
    @Column
    private LocalDateTime snapshotDate;
    
    // Constructors
    public ActivityMemento() {
        this.snapshotDate = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        if (snapshotDate == null) {
            snapshotDate = LocalDateTime.now();
        }
    }
    
    // Getters and Setters
    public Long getMementoId() { return mementoId; }
    public void setMementoId(Long mementoId) { this.mementoId = mementoId; }
    
    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDateTime getActivityDate() { return activityDate; }
    public void setActivityDate(LocalDateTime activityDate) { this.activityDate = activityDate; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    
    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
    
    public LocalDateTime getSnapshotDate() { return snapshotDate; }
    public void setSnapshotDate(LocalDateTime snapshotDate) { this.snapshotDate = snapshotDate; }
}
