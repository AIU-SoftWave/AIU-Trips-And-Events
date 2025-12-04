package com.aiu.trips.pattern.memento;

import com.aiu.trips.enums.ActivityCategory;
import com.aiu.trips.enums.ActivityStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ActivityMemento - Stores snapshot of Activity state for history/undo functionality
 */
@Entity
@Table(name = "activity_mementos")
public class ActivityMemento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "activity_id", nullable = false)
    private Long activityId;
    
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
    
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    
    @Enumerated(EnumType.STRING)
    private ActivityCategory category;
    
    @Enumerated(EnumType.STRING)
    private ActivityStatus status;
    
    @Column(name = "snapshot_date")
    private LocalDateTime snapshotDate;
    
    @PrePersist
    protected void onCreate() {
        if (snapshotDate == null) {
            snapshotDate = LocalDateTime.now();
        }
    }
    
    public ActivityMemento() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getActivityId() {
        return activityId;
    }
    
    public void setActivityId(Long activityId) {
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
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public ActivityCategory getCategory() {
        return category;
    }
    
    public void setCategory(ActivityCategory category) {
        this.category = category;
    }
    
    public ActivityStatus getStatus() {
        return status;
    }
    
    public void setStatus(ActivityStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getSnapshotDate() {
        return snapshotDate;
    }
    
    public void setSnapshotDate(LocalDateTime snapshotDate) {
        this.snapshotDate = snapshotDate;
    }
}
