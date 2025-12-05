package com.aiu.trips.memento;

import com.aiu.trips.factory.IModelFactory;
import com.aiu.trips.model.Activity;
import com.aiu.trips.model.ActivityMemento;
import com.aiu.trips.repository.ActivityMementoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Memento Pattern Caretaker for Activity
 * Manages activity history snapshots
 */
@Component
public class ActivityHistoryCaretaker {
    
    @Autowired
    private ActivityMementoRepository mementoRepository;
    
    @Autowired
    private IModelFactory modelFactory;
    
    /**
     * Saves a snapshot of activity state
     * @param activity Activity to snapshot
     */
    public void save(Activity activity) {
        ActivityMemento memento = new ActivityMemento();
        memento.setActivityId(activity.getActivityId());
        memento.setName(activity.getName());
        memento.setDescription(activity.getDescription());
        memento.setActivityDate(activity.getActivityDate());
        memento.setLocation(activity.getLocation());
        memento.setCapacity(activity.getCapacity());
        memento.setAvailableSeats(activity.getAvailableSeats());
        
        mementoRepository.save(memento);
    }
    
    /**
     * Gets the last memento for an activity
     * @param activityId Activity ID
     * @return Last memento or null
     */
    public ActivityMemento getLastMemento(Long activityId) {
        return mementoRepository
            .findFirstByActivityIdOrderBySnapshotDateDesc(activityId)
            .orElse(null);
    }
    
    /**
     * Restores activity from memento
     * @param activity Activity to restore
     * @param memento Memento to restore from
     */
    public void restore(Activity activity, ActivityMemento memento) {
        if (memento != null) {
            activity.setName(memento.getName());
            activity.setDescription(memento.getDescription());
            activity.setActivityDate(memento.getActivityDate());
            activity.setLocation(memento.getLocation());
            activity.setCapacity(memento.getCapacity());
            activity.setAvailableSeats(memento.getAvailableSeats());
        }
    }
}
