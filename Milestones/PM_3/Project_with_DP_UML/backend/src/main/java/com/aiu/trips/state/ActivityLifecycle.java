package com.aiu.trips.state;

import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.model.Activity;
import org.springframework.stereotype.Component;

/**
 * State Pattern Context for Activity Lifecycle
 * Manages activity state transitions
 */
@Component
public class ActivityLifecycle {
    private ActivityState state;
    
    public ActivityLifecycle() {
        // Default state is Upcoming
        this.state = new UpcomingState();
    }
    
    /**
     * Sets the current state
     * @param state New state
     */
    public void setState(ActivityState state) {
        this.state = state;
    }
    
    /**
     * Gets the current state based on activity status
     * @param activity Activity
     * @return Current state
     */
    public ActivityState getStateFromActivity(Activity activity) {
        if (activity.getStatus() == null) {
            return new UpcomingState();
        }
        
        switch (activity.getStatus()) {
            case UPCOMING:
                return new UpcomingState();
            case COMPLETED:
                return new CompletedState();
            case CANCELLED:
                return new CancelledState();
            default:
                return new UpcomingState();
        }
    }
    
    /**
     * Publishes the activity (delegates to current state)
     * @param activity Activity to publish
     * @return Updated activity
     */
    public Activity publish(Activity activity) {
        this.state = getStateFromActivity(activity);
        return state.publish(activity);
    }
    
    /**
     * Completes the activity (delegates to current state)
     * @param activity Activity to complete
     * @return Updated activity
     */
    public Activity complete(Activity activity) {
        this.state = getStateFromActivity(activity);
        return state.complete(activity);
    }
    
    /**
     * Cancels the activity (delegates to current state)
     * @param activity Activity to cancel
     * @return Updated activity
     */
    public Activity cancel(Activity activity) {
        this.state = getStateFromActivity(activity);
        return state.cancel(activity);
    }
}
