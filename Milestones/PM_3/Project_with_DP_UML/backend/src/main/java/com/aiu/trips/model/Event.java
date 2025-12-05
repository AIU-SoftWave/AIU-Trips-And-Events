package com.aiu.trips.model;

import com.aiu.trips.enums.ActivityCategory;
import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.enums.ActivityType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
public class Event extends Activity {
    
    @ElementCollection
    @CollectionTable(name = "event_speakers", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "speaker")
    private List<String> speakers;
    
    @Column
    private String topic;
    
    @Column
    private String venue;
    
    @Column(length = 2000)
    private String agenda;
    
    // Constructors
    public Event() {
        super();
    }
    
    public Event(String name, String description, LocalDateTime activityDate,
                String location, Integer capacity, BigDecimal price,
                ActivityCategory category, String topic, String venue) {
        super(name, description, activityDate, location, capacity, price, category, ActivityType.EVENT);
        this.topic = topic;
        this.venue = venue;
    }
    
    // Getters and Setters
    public List<String> getSpeakers() { return speakers; }
    public void setSpeakers(List<String> speakers) { this.speakers = speakers; }
    
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    
    public String getAgenda() { return agenda; }
    public void setAgenda(String agenda) { this.agenda = agenda; }
    
    // Backward compatibility methods for existing services
    public Long getId() { return getActivityId(); }
    public void setId(Long id) { setActivityId(id); }
    
    public String getTitle() { return getName(); }
    public void setTitle(String title) { setName(title); }
    
    public LocalDateTime getStartDate() { return getActivityDate(); }
    public void setStartDate(LocalDateTime startDate) { setActivityDate(startDate); }
    
    public LocalDateTime getEndDate() { return getActivityDate(); } // Same as start for now
    public void setEndDate(LocalDateTime endDate) { /* Ignored for now */ }
    
    public Double getPriceAsDouble() { return super.getPrice() != null ? super.getPrice().doubleValue() : null; }
    public void setPriceFromDouble(Double price) { super.setPrice(price != null ? BigDecimal.valueOf(price) : null); }
    
    public String getImageUrl() { return null; } // Not in new schema
    public void setImageUrl(String imageUrl) { /* Ignored */ }
    
    public User getCreatedBy() { return getOrganizer(); }
    public void setCreatedBy(User user) { setOrganizer(user); }
    
    public com.aiu.trips.enums.EventStatus getEventStatus() {
        if (getStatus() == null) return null;
        switch(getStatus()) {
            case UPCOMING: return com.aiu.trips.enums.EventStatus.ACTIVE;
            case COMPLETED: return com.aiu.trips.enums.EventStatus.COMPLETED;
            case CANCELLED: return com.aiu.trips.enums.EventStatus.CANCELLED;
            default: return com.aiu.trips.enums.EventStatus.ACTIVE;
        }
    }
    
    public void setEventStatus(com.aiu.trips.enums.EventStatus status) {
        if (status == null) return;
        switch(status) {
            case ACTIVE: setStatus(ActivityStatus.UPCOMING); break;
            case COMPLETED: setStatus(ActivityStatus.COMPLETED); break;
            case CANCELLED: setStatus(ActivityStatus.CANCELLED); break;
        }
    }
    
    public com.aiu.trips.enums.EventType getEventType() {
        return com.aiu.trips.enums.EventType.EVENT;
    }
    
    public void setEventType(com.aiu.trips.enums.EventType type) {
        // Ignored, always EVENT
    }
}
