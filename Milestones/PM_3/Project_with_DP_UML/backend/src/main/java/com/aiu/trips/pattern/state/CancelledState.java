package com.aiu.trips.pattern.state;

import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.model.Activity;

/**
 * Cancelled state - activity has been cancelled
 */
public class CancelledState implements ActivityState {
    
    @Override
    public Activity publish(Activity activity) {
        // Re-publish by changing to upcoming
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
        // Already cancelled
        return activity;
    }
}
