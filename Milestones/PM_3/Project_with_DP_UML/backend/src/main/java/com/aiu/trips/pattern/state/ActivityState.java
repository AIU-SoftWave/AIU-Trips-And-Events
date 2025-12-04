package com.aiu.trips.pattern.state;

import com.aiu.trips.model.Activity;

/**
 * State pattern interface for activity lifecycle states
 */
public interface ActivityState {
    /**
     * Publishes the activity (makes it available)
     * @param activity The activity to publish
     * @return Updated activity
     */
    Activity publish(Activity activity);
    
    /**
     * Completes the activity
     * @param activity The activity to complete
     * @return Updated activity
     */
    Activity complete(Activity activity);
    
    /**
     * Cancels the activity
     * @param activity The activity to cancel
     * @return Updated activity
     */
    Activity cancel(Activity activity);
}
