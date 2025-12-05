package com.aiu.trips.model;

import com.aiu.trips.enums.ActivityCategory;
import com.aiu.trips.enums.ActivityType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip extends Activity {
    
    @Column
    private String destination;
    
    @Column
    private Integer durationDays;
    
    @Column
    private String transportMode;
    
    @Column
    private String startLocation;
    
    @Column
    private String endLocation;
    
    @Column(length = 2000)
    private String itinerary;
    
    // Constructors
    public Trip() {
        super();
    }
    
    public Trip(String name, String description, LocalDateTime activityDate,
               String location, Integer capacity, BigDecimal price,
               ActivityCategory category, String destination, Integer durationDays,
               String transportMode) {
        super(name, description, activityDate, location, capacity, price, category, ActivityType.TRIP);
        this.destination = destination;
        this.durationDays = durationDays;
        this.transportMode = transportMode;
    }
    
    // Getters and Setters
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    
    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }
    
    public String getTransportMode() { return transportMode; }
    public void setTransportMode(String transportMode) { this.transportMode = transportMode; }
    
    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }
    
    public String getEndLocation() { return endLocation; }
    public void setEndLocation(String endLocation) { this.endLocation = endLocation; }
    
    public String getItinerary() { return itinerary; }
    public void setItinerary(String itinerary) { this.itinerary = itinerary; }
}
