package com.aiu.trips.model;

import jakarta.persistence.*;
import java.util.ArrayList;

/**
 * Trip entity - represents trips like field trips, adventure trips, cultural visits
 * Extends Activity with trip-specific fields
 */
@Entity
@DiscriminatorValue("TRIP")
public class Trip extends Activity {
    
    @Column(length = 500)
    private String destination;
    
    @Column(name = "duration_days")
    private Integer durationDays;
    
    @Column(name = "transport_mode", length = 100)
    private String transportMode;
    
    @Column(name = "start_location", length = 500)
    private String startLocation;
    
    @Column(name = "end_location", length = 500)
    private String endLocation;
    
    @Column(length = 2000)
    private String itinerary;
    
    public Trip() {
        super();
    }
    
    // Clone method for Prototype pattern
    @Override
    public Activity clone() {
        Trip cloned = new Trip();
        cloned.setName(this.getName());
        cloned.setDescription(this.getDescription());
        cloned.setActivityDate(this.getActivityDate());
        cloned.setLocation(this.getLocation());
        cloned.setCapacity(this.getCapacity());
        cloned.setPrice(this.getPrice());
        cloned.setCategory(this.getCategory());
        cloned.setStatus(this.getStatus());
        cloned.setOrganizerId(this.getOrganizerId());
        cloned.setDestination(this.destination);
        cloned.setDurationDays(this.durationDays);
        cloned.setTransportMode(this.transportMode);
        cloned.setStartLocation(this.startLocation);
        cloned.setEndLocation(this.endLocation);
        cloned.setItinerary(this.itinerary);
        return cloned;
    }
    
    // Getters and Setters
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public Integer getDurationDays() {
        return durationDays;
    }
    
    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }
    
    public String getTransportMode() {
        return transportMode;
    }
    
    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }
    
    public String getStartLocation() {
        return startLocation;
    }
    
    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }
    
    public String getEndLocation() {
        return endLocation;
    }
    
    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }
    
    public String getItinerary() {
        return itinerary;
    }
    
    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }
}
