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
        
        Event cloned = new Event();
        cloned.setTitle(prototype.getTitle());
        cloned.setDescription(prototype.getDescription());
        cloned.setType(EventType.EVENT);
        cloned.setLocation(prototype.getLocation());
        cloned.setPrice(prototype.getPrice());
        cloned.setCapacity(prototype.getCapacity());
        cloned.setImageUrl(prototype.getImageUrl());
        
        return cloned;
    }
}
