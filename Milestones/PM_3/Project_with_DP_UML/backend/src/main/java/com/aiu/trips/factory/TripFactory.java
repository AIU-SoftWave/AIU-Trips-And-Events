package com.aiu.trips.factory;

import com.aiu.trips.builder.IActivityDirector;
import com.aiu.trips.builder.TripBuilder;
import com.aiu.trips.dto.ActivityDTO;
import com.aiu.trips.enums.ActivityCategory;
import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.model.Activity;
import com.aiu.trips.model.Trip;
import com.aiu.trips.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Concrete Factory for creating Trip objects
 * Implements Abstract Factory pattern
 */
@Component
public class TripFactory implements IActivityFactory {
    
    @Autowired
    private IActivityDirector director;
    
    @Autowired
    private TripBuilder builder;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Activity createActivity(ActivityDTO data) {
        // Use director and builder to construct
        director.setBuilder(builder);
        ActivityDTO builtData = director.makeNormalTrip(data);
        
        // Convert DTO to Entity
        Trip trip = new Trip();
        trip.setName(builtData.getName());
        trip.setDescription(builtData.getDescription());
        trip.setActivityDate(builtData.getActivityDate());
        trip.setLocation(builtData.getLocation());
        trip.setCapacity(builtData.getCapacity());
        trip.setAvailableSeats(builtData.getAvailableSeats());
        trip.setPrice(builtData.getPrice());
        trip.setCategory(builtData.getCategory() != null ? builtData.getCategory() : ActivityCategory.ADVENTURE_TRIP);
        trip.setStatus(builtData.getStatus() != null ? builtData.getStatus() : ActivityStatus.UPCOMING);
        
        // Set organizer if provided
        if (builtData.getOrganizerId() != null) {
            userRepository.findById(builtData.getOrganizerId())
                .ifPresent(trip::setOrganizer);
        }
        
        // Trip-specific fields
        trip.setDestination(builtData.getDestination());
        trip.setDurationDays(builtData.getDurationDays());
        trip.setTransportMode(builtData.getTransportMode());
        trip.setStartLocation(builtData.getStartLocation());
        trip.setEndLocation(builtData.getEndLocation());
        trip.setItinerary(builtData.getItinerary());
        
        return trip;
    }
    
    @Override
    public Activity clonePrototype(Activity activity) {
        if (!(activity instanceof Trip)) {
            throw new IllegalArgumentException("Activity must be a Trip");
        }
        
        Trip source = (Trip) activity;
        Trip clone = new Trip();
        
        // Copy all fields
        clone.setName(source.getName() + " (Copy)");
        clone.setDescription(source.getDescription());
        clone.setActivityDate(source.getActivityDate());
        clone.setLocation(source.getLocation());
        clone.setCapacity(source.getCapacity());
        clone.setAvailableSeats(source.getCapacity());
        clone.setPrice(source.getPrice());
        clone.setCategory(source.getCategory());
        clone.setStatus(ActivityStatus.UPCOMING);
        clone.setOrganizer(source.getOrganizer());
        
        // Trip-specific
        clone.setDestination(source.getDestination());
        clone.setDurationDays(source.getDurationDays());
        clone.setTransportMode(source.getTransportMode());
        clone.setStartLocation(source.getStartLocation());
        clone.setEndLocation(source.getEndLocation());
        clone.setItinerary(source.getItinerary());
        
        return clone;
    }
}
