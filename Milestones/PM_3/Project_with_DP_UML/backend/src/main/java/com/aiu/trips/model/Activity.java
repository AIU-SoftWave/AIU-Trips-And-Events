package com.aiu.trips.model;

import com.aiu.trips.enums.ActivityCategory;
import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.enums.ActivityType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Activity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 2000)
    private String description;
    
    @Column(nullable = false)
    private LocalDateTime activityDate;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private Integer capacity;
    
    @Column(nullable = false)
    private Integer availableSeats;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityCategory category;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityStatus status;
    
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType type;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    // Constructors
    public Activity() {}
    
    public Activity(String name, String description, LocalDateTime activityDate, 
                   String location, Integer capacity, BigDecimal price, 
                   ActivityCategory category, ActivityType type) {
        this.name = name;
        this.description = description;
        this.activityDate = activityDate;
        this.location = location;
        this.capacity = capacity;
        this.availableSeats = capacity;
        this.price = price;
        this.category = category;
        this.type = type;
        this.status = ActivityStatus.UPCOMING;
        this.createdAt = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (availableSeats == null) {
            availableSeats = capacity;
        }
        if (status == null) {
            status = ActivityStatus.UPCOMING;
        }
    }
    
    // Getters and Setters
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
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public ActivityCategory getCategory() { return category; }
    public void setCategory(ActivityCategory category) { this.category = category; }
    
    public ActivityStatus getStatus() { return status; }
    public void setStatus(ActivityStatus status) { this.status = status; }
    
    public User getOrganizer() { return organizer; }
    public void setOrganizer(User organizer) { this.organizer = organizer; }
    
    public ActivityType getType() { return type; }
    public void setType(ActivityType type) { this.type = type; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
