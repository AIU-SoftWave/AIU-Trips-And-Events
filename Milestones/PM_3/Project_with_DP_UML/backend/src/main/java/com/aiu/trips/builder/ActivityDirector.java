package com.aiu.trips.builder;

import com.aiu.trips.dto.ActivityDTO;
import com.aiu.trips.enums.ActivityStatus;
import org.springframework.stereotype.Component;

/**
 * Director for Activity Builder Pattern
 * Orchestrates the construction process using builders
 */
@Component
public class ActivityDirector implements IActivityDirector {
    
    private IActivityBuilder builder;
    
    @Override
    public void setBuilder(IActivityBuilder builder) {
        this.builder = builder;
    }
    
    @Override
    public ActivityDTO constructFrom(ActivityDTO data) {
        builder.reset();
        builder.setBasicInfo(data);
        builder.setSchedule(data.getActivityDate(), data.getLocation());
        builder.setCapacity(data.getCapacity());
        builder.setPricing(data.getPrice());
        builder.setOrganizer(data.getOrganizerId());
        builder.setDetails();
        return builder.getResult();
    }
    
    @Override
    public ActivityDTO makeNormalEvent(ActivityDTO data) {
        builder.reset();
        builder.setBasicInfo(data);
        builder.setSchedule(data.getActivityDate(), data.getLocation());
        builder.setCapacity(data.getCapacity() != null ? data.getCapacity() : 100);
        builder.setPricing(data.getPrice());
        builder.setOrganizer(data.getOrganizerId());
        
        // Set event-specific details if builder is EventBuilder
        if (builder instanceof EventBuilder) {
            ((EventBuilder) builder).setEventDetails(data.getTopic(), data.getVenue());
        }
        
        ActivityDTO result = builder.getResult();
        result.setStatus(ActivityStatus.UPCOMING);
        return result;
    }
    
    @Override
    public ActivityDTO makeNormalTrip(ActivityDTO data) {
        builder.reset();
        builder.setBasicInfo(data);
        builder.setSchedule(data.getActivityDate(), data.getLocation());
        builder.setCapacity(data.getCapacity() != null ? data.getCapacity() : 50);
        builder.setPricing(data.getPrice());
        builder.setOrganizer(data.getOrganizerId());
        
        // Set trip-specific details if builder is TripBuilder
        if (builder instanceof TripBuilder) {
            ((TripBuilder) builder).setTripDetails(
                data.getDestination(),
                data.getDurationDays(),
                data.getTransportMode()
            );
        }
        
        ActivityDTO result = builder.getResult();
        result.setStatus(ActivityStatus.UPCOMING);
        return result;
    }
    
    @Override
    public ActivityDTO makeHighCapacityEvent(ActivityDTO data, Integer capacity) {
        builder.reset();
        builder.setBasicInfo(data);
        builder.setSchedule(data.getActivityDate(), data.getLocation());
        builder.setCapacity(capacity);
        builder.setPricing(data.getPrice());
        builder.setOrganizer(data.getOrganizerId());
        
        // Set event-specific details if builder is EventBuilder
        if (builder instanceof EventBuilder) {
            ((EventBuilder) builder).setEventDetails(data.getTopic(), data.getVenue());
        }
        
        ActivityDTO result = builder.getResult();
        result.setStatus(ActivityStatus.UPCOMING);
        return result;
    }
    
    @Override
    public ActivityDTO makeHighCapacityTrip(ActivityDTO data, Integer capacity) {
        builder.reset();
        builder.setBasicInfo(data);
        builder.setSchedule(data.getActivityDate(), data.getLocation());
        builder.setCapacity(capacity);
        builder.setPricing(data.getPrice());
        builder.setOrganizer(data.getOrganizerId());
        
        // Set trip-specific details if builder is TripBuilder
        if (builder instanceof TripBuilder) {
            ((TripBuilder) builder).setTripDetails(
                data.getDestination(),
                data.getDurationDays(),
                data.getTransportMode()
            );
        }
        
        ActivityDTO result = builder.getResult();
        result.setStatus(ActivityStatus.UPCOMING);
        return result;
    }
}
