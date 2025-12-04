package com.aiu.trips.factory.activity;

import com.aiu.trips.enums.EventType;
import com.aiu.trips.model.Event;
import org.springframework.stereotype.Component;

/**
 * Abstract Factory Pattern - Concrete factory for creating Event objects
 */
@Component
public class EventFactory implements IActivityFactory {
    
    @Override
    public Event createActivity() {
        Event event = new Event();
        event.setType(EventType.EVENT);
        return event;
    }
    
    @Override
    public Event clonePrototype(Event prototype) {
        if (prototype == null) {
            throw new IllegalArgumentException("Prototype cannot be null");
        }
        
        // Use Event's built-in clone method to avoid duplication
        return prototype.clone();
    }
}
