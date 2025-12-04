package com.aiu.trips.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Event entity - concrete implementation of Activity
 * Based on After DP Data_Layer diagram
 */
@Entity
@DiscriminatorValue("EVENT")
public class Event extends Activity {

    @ElementCollection
    @CollectionTable(name = "event_speakers", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "speaker")
    private List<String> speakers;

    @Column(length = 500)
    private String topic;

    @Column(length = 500)
    private String venue;

    @Column(length = 2000)
    private String agenda;

    // Constructors
    public Event() {
        super();
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
}
