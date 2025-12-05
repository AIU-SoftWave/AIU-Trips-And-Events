package com.aiu.trips.state;

import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.model.Activity;

/**
 * Concrete State: Completed
 * Represents a completed activity
 */
public class CompletedState implements ActivityState {
    
    @Override
    public Activity publish(Activity activity) {
        // Cannot re-publish a completed activity
        throw new IllegalStateException("Cannot publish a completed activity");
    }
    
    @Override
    public Activity complete(Activity activity) {
        // Already completed - no change
        activity.setStatus(ActivityStatus.COMPLETED);
        return activity;
    }
    
    @Override
    public Activity cancel(Activity activity) {
        // Cannot cancel a completed activity
        throw new IllegalStateException("Cannot cancel a completed activity");
    }
}
