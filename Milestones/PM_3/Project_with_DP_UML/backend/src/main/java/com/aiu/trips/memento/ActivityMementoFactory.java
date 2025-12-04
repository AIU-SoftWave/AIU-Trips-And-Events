package com.aiu.trips.memento;

import com.aiu.trips.model.Activity;
import org.springframework.stereotype.Component;

/**
 * Memento Pattern - Factory for creating Activity mementos
 * Based on After DP Data_Layer diagram
 */
@Component
public class ActivityMementoFactory {
    
    public com.aiu.trips.model.ActivityMemento createFromActivity(Activity activity) {
        return new com.aiu.trips.model.ActivityMemento(
            activity.getActivityId(),
            activity.getName(),
            activity.getDescription(),
            activity.getActivityDate(),
            activity.getLocation(),
            activity.getCapacity(),
            activity.getAvailableSeats()
        );
    }
    
    public void restoreFromMemento(Activity activity, com.aiu.trips.model.ActivityMemento memento) {
        activity.setName(memento.getName());
        activity.setDescription(memento.getDescription());
        activity.setActivityDate(memento.getActivityDate());
        activity.setLocation(memento.getLocation());
        activity.setCapacity(memento.getCapacity());
        activity.setAvailableSeats(memento.getAvailableSeats());
    }
}
