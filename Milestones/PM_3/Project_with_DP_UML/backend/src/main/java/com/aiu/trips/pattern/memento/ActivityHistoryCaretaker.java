package com.aiu.trips.pattern.memento;

import com.aiu.trips.model.Activity;
import com.aiu.trips.repository.ActivityMementoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Caretaker for Activity mementos - manages saving and retrieving activity snapshots
 */
@Component
public class ActivityHistoryCaretaker {
    
    @Autowired
    private ActivityMementoRepository mementoRepository;
    
    /**
     * Saves a snapshot of the activity's current state
     * @param activity The activity to snapshot
     */
    public void save(Activity activity) {
        if (activity == null || activity.getActivityId() == null) {
            return;
        }
        
        ActivityMemento memento = ActivityMementoFactory.createFromActivity(activity);
        mementoRepository.save(memento);
    }
    
    /**
     * Retrieves the most recent memento for an activity
     * @param activityId The activity ID
     * @return The last memento, or null if none exists
     */
    public ActivityMemento getLastMemento(Long activityId) {
        return mementoRepository.findFirstByActivityIdOrderBySnapshotDateDesc(activityId)
                .orElse(null);
    }
}
