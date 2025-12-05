package com.aiu.trips.state;

import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.model.Activity;

/**
 * Concrete State: Cancelled
 * Represents a cancelled activity
 */
public class CancelledState implements ActivityState {
    
    @Override
    public Activity publish(Activity activity) {
        // Can re-publish a cancelled activity (reopen it)
        activity.setStatus(ActivityStatus.UPCOMING);
        return activity;
    }
    
    @Override
    public Activity complete(Activity activity) {
        // Cannot complete a cancelled activity
        throw new IllegalStateException("Cannot complete a cancelled activity");
    }
    
    @Override
    public Activity cancel(Activity activity) {
        // Already cancelled - no change
        activity.setStatus(ActivityStatus.CANCELLED);
        return activity;
    }
}
