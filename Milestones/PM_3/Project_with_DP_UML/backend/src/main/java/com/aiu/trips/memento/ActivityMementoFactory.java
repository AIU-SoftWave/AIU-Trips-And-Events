package com.aiu.trips.memento;

import com.aiu.trips.model.Event;
import org.springframework.stereotype.Component;

/**
 * Memento Pattern - Factory for creating Activity mementos
 */
@Component
public class ActivityMementoFactory {
    
    public ActivityMemento createMemento(Event event) {
        return new ActivityMemento(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getType(),
            event.getStartDate(),
            event.getEndDate(),
            event.getLocation(),
            event.getPrice(),
            event.getCapacity(),
            event.getAvailableSeats(),
            event.getStatus()
        );
    }
    
    public void restoreFromMemento(Event event, ActivityMemento memento) {
        event.setTitle(memento.getTitle());
        event.setDescription(memento.getDescription());
        event.setType(memento.getType());
        event.setStartDate(memento.getStartDate());
        event.setEndDate(memento.getEndDate());
        event.setLocation(memento.getLocation());
        event.setPrice(memento.getPrice());
        event.setCapacity(memento.getCapacity());
        event.setAvailableSeats(memento.getAvailableSeats());
        event.setStatus(memento.getStatus());
    }
}
