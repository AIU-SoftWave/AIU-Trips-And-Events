package com.aiu.trips.service;

import com.aiu.trips.exception.ResourceNotFoundException;
import com.aiu.trips.model.Trip;
import com.aiu.trips.repository.TripRepository;
import com.aiu.trips.service.interfaces.ITripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TripService implements ITripService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    @Transactional
    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    @Transactional
    public Trip updateTrip(Long tripId, Trip tripData) {
        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new ResourceNotFoundException("Trip not found: " + tripId));

        // Update basic event fields
        trip.setTitle(tripData.getTitle());
        trip.setDescription(tripData.getDescription());
        trip.setStartDate(tripData.getStartDate());
        trip.setEndDate(tripData.getEndDate());
        trip.setLocation(tripData.getLocation());
        trip.setPrice(tripData.getPrice());
        trip.setCapacity(tripData.getCapacity());
        trip.setImageUrl(tripData.getImageUrl());
        
        // Update trip-specific fields
        trip.setDestination(tripData.getDestination());
        trip.setDuration(tripData.getDuration());
        trip.setItinerary(tripData.getItinerary());
        trip.setIncludedServices(tripData.getIncludedServices());
        trip.setTransportationType(tripData.getTransportationType());

        return tripRepository.save(trip);
    }

    @Override
    @Transactional
    public boolean deleteTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new ResourceNotFoundException("Trip not found: " + tripId));
        
        tripRepository.delete(trip);
        return true;
    }

    @Override
    public Trip getTripById(Long tripId) {
        return tripRepository.findById(tripId)
            .orElseThrow(() -> new ResourceNotFoundException("Trip not found: " + tripId));
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public List<Trip> searchTrips(String keyword) {
        return tripRepository.findByDestinationContaining(keyword);
    }
}
