package com.aiu.trips.state;

import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.model.Activity;

/**
 * Concrete State: Upcoming
 * Represents an upcoming activity
 */
public class UpcomingState implements ActivityState {
    
    @Override
    public Activity publish(Activity activity) {
        // Already published/upcoming - no change
        activity.setStatus(ActivityStatus.UPCOMING);
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
