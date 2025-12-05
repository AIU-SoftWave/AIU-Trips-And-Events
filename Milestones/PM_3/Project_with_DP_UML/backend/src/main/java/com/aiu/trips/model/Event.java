package com.aiu.trips.model;

import com.aiu.trips.enums.ActivityCategory;
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
}
