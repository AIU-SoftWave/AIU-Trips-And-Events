package com.aiu.trips.service.interfaces;

import com.aiu.trips.model.Trip;

import java.util.List;

public interface ITripService {
    Trip createTrip(Trip trip);
    Trip updateTrip(Long tripId, Trip tripData);
    boolean deleteTrip(Long tripId);
    Trip getTripById(Long tripId);
    List<Trip> getAllTrips();
    List<Trip> searchTrips(String keyword);
}
