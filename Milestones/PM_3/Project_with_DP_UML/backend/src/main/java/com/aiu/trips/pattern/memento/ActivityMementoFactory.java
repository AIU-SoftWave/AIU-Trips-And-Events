package com.aiu.trips.pattern.memento;

import com.aiu.trips.model.Activity;

/**
 * Factory for creating ActivityMemento objects from Activity entities
 */
public class ActivityMementoFactory {
    
    /**
     * Creates a memento from an Activity instance
     * @param activity The activity to create memento from
     * @return ActivityMemento snapshot
     */
    public static ActivityMemento createFromActivity(Activity activity) {
        if (activity == null) {
            return null;
        }
        
        ActivityMemento memento = new ActivityMemento();
        memento.setActivityId(activity.getActivityId());
        memento.setName(activity.getName());
        memento.setDescription(activity.getDescription());
        memento.setActivityDate(activity.getActivityDate());
        memento.setLocation(activity.getLocation());
        memento.setCapacity(activity.getCapacity());
        memento.setAvailableSeats(activity.getAvailableSeats());
        memento.setPrice(activity.getPrice());
        memento.setCategory(activity.getCategory());
        memento.setStatus(activity.getStatus());
        
        return memento;
    }
}
