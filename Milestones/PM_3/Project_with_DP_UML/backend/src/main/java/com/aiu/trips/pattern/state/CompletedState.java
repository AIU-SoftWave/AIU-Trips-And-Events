package com.aiu.trips.pattern.state;

import com.aiu.trips.model.Activity;

/**
 * Completed state - activity has finished
 */
public class CompletedState implements ActivityState {
    
    @Override
    public Activity publish(Activity activity) {
        // Cannot republish a completed activity
        throw new IllegalStateException("Cannot publish a completed activity");
    }
    
    @Override
    public Activity complete(Activity activity) {
        // Already completed
        return activity;
    }
    
    @Override
    public Activity cancel(Activity activity) {
        // Cannot cancel a completed activity
        throw new IllegalStateException("Cannot cancel a completed activity");
    }
}
