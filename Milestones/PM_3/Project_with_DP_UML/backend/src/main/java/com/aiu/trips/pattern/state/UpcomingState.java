package com.aiu.trips.pattern.state;

import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.model.Activity;

/**
 * Upcoming state - activity is scheduled and available
 */
public class UpcomingState implements ActivityState {
    
    @Override
    public Activity publish(Activity activity) {
        // Already published
        return activity;
    }
    
    @Override
    public Activity complete(Activity activity) {
        // Transition to completed
        activity.setStatus(ActivityStatus.COMPLETED);
        return activity;
    }
    
    @Override
    public Activity cancel(Activity activity) {
        // Transition to cancelled
        activity.setStatus(ActivityStatus.CANCELLED);
        return activity;
    }
}
