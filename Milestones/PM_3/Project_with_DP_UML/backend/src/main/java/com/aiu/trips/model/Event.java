package com.aiu.trips.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Event entity - represents events like conferences, seminars, workshops
 * Extends Activity with event-specific fields
 */
@Entity
@DiscriminatorValue("EVENT")
public class Event extends Activity {
    
    @ElementCollection
    @CollectionTable(name = "event_speakers", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "speaker")
    private List<String> speakers = new ArrayList<>();
    
    @Column(length = 500)
    private String topic;
    
    @Column(length = 500)
    private String venue;
    
    @Column(length = 2000)
    private String agenda;
    
    // For backward compatibility with old Event fields
    @Transient
    private String imageUrl;
    
    @Transient
    private LocalDateTime endDate;
    
    public Event() {
        super();
    }
    
    // Clone method for Prototype pattern
    @Override
    public Activity clone() {
        Event cloned = new Event();
        cloned.setName(this.getName());
        cloned.setDescription(this.getDescription());
        cloned.setActivityDate(this.getActivityDate());
        cloned.setLocation(this.getLocation());
        cloned.setCapacity(this.getCapacity());
        cloned.setPrice(this.getPrice());
        cloned.setCategory(this.getCategory());
        cloned.setStatus(this.getStatus());
        cloned.setOrganizerId(this.getOrganizerId());
        cloned.setSpeakers(new ArrayList<>(this.speakers));
        cloned.setTopic(this.topic);
        cloned.setVenue(this.venue);
        cloned.setAgenda(this.agenda);
        return cloned;
    }
    
    // Getters and Setters
    public List<String> getSpeakers() {
        return speakers;
    }
    
    public void setSpeakers(List<String> speakers) {
        this.speakers = speakers;
    }
    
    public String getTopic() {
        return topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    public String getVenue() {
        return venue;
    }
    
    public void setVenue(String venue) {
        this.venue = venue;
    }
    
    public String getAgenda() {
        return agenda;
    }
    
    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }
    
    // Backward compatibility methods
    public Long getId() {
        return getActivityId();
    }
    
    public void setId(Long id) {
        setActivityId(id);
    }
    
    public String getTitle() {
        return getName();
    }
    
    public void setTitle(String title) {
        setName(title);
    }
    
    public LocalDateTime getStartDate() {
        return getActivityDate();
    }
    
    public void setStartDate(LocalDateTime startDate) {
        setActivityDate(startDate);
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    // Price compatibility
    public void setPrice(Double price) {
        if (price != null) {
            setPrice(BigDecimal.valueOf(price));
        }
    }
    
    public Double getPriceAsDouble() {
        return getPrice() != null ? getPrice().doubleValue() : null;
    }
}
