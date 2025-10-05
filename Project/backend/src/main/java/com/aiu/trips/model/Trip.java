package com.aiu.trips.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trips")
public class Trip extends Event {
    
    public Trip() {
        super();
    }
    
    @Column
    private String destination;
    
    @Column
    private String departureLocation;
    
    @Column
    private LocalDateTime departureTime;
    
    @Column
    private LocalDateTime returnTime;
    
    @Column
    private String transportationType;
    
    @Column(length = 2000)
    private String accommodationDetails;
    
    @ElementCollection
    @CollectionTable(name = "trip_itinerary", joinColumns = @JoinColumn(name = "trip_id"))
    @Column(name = "itinerary_item")
    private List<String> itinerary = new ArrayList<>();
    
    // Getters and Setters
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    
    public String getDepartureLocation() { return departureLocation; }
    public void setDepartureLocation(String departureLocation) { this.departureLocation = departureLocation; }
    
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    
    public LocalDateTime getReturnTime() { return returnTime; }
    public void setReturnTime(LocalDateTime returnTime) { this.returnTime = returnTime; }
    
    public String getTransportationType() { return transportationType; }
    public void setTransportationType(String transportationType) { this.transportationType = transportationType; }
    
    public String getAccommodationDetails() { return accommodationDetails; }
    public void setAccommodationDetails(String accommodationDetails) { this.accommodationDetails = accommodationDetails; }
    
    public List<String> getItinerary() { return itinerary; }
    public void setItinerary(List<String> itinerary) { this.itinerary = itinerary; }
}
