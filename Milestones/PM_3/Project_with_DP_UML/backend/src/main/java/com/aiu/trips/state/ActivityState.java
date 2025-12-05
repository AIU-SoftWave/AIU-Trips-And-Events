package com.aiu.trips.state;

import com.aiu.trips.model.Activity;

/**
 * State Pattern Interface for Activity Lifecycle
 * Defines operations that can be performed in different states
 */
public interface ActivityState {
    /**
     * Publishes the activity
     * @param activity Activity to publish
     * @return Updated activity
     */
    Activity publish(Activity activity);
    
    /**
     * Completes the activity
     * @param activity Activity to complete
     * @return Updated activity
     */
    Activity complete(Activity activity);
    
    /**
     * Cancels the activity
     * @param activity Activity to cancel
     * @return Updated activity
     */
    Activity cancel(Activity activity);
}
