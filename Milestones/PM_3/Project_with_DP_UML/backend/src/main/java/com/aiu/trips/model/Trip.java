package com.aiu.trips.model;

import jakarta.persistence.*;

/**
 * Trip entity - concrete implementation of Activity
 * Based on After DP Data_Layer diagram
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

    // Constructors
    public Trip() {
        super();
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
