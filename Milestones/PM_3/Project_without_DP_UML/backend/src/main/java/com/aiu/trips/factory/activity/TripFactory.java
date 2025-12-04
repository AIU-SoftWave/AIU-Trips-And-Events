package com.aiu.trips.factory.activity;

import com.aiu.trips.enums.EventType;
import com.aiu.trips.model.Event;
import org.springframework.stereotype.Component;

/**
 * Abstract Factory Pattern - Concrete factory for creating Trip objects
 */
@Component
public class TripFactory implements IActivityFactory {
    
    @Override
    public Event createActivity() {
        Event trip = new Event();
        trip.setType(EventType.TRIP);
        return trip;
    }
    
    @Override
    public Event clonePrototype(Event prototype) {
        if (prototype == null) {
            throw new IllegalArgumentException("Prototype cannot be null");
        }
        
        // Use Event's built-in clone method to avoid duplication
        Event cloned = prototype.clone();
        // Ensure type is set to TRIP for TripFactory
        cloned.setType(EventType.TRIP);
        return cloned;
    }
}
