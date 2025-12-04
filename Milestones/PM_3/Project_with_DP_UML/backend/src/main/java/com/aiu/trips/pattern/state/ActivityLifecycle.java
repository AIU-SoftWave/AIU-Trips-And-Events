package com.aiu.trips.pattern.state;

import com.aiu.trips.enums.ActivityStatus;
import com.aiu.trips.model.Activity;
import org.springframework.stereotype.Component;

/**
 * Context class for Activity State pattern
 * Manages activity lifecycle state transitions
 */
@Component
public class ActivityLifecycle {
    
    private ActivityState state;
    
    public ActivityLifecycle() {
        // Default state is upcoming
        this.state = new UpcomingState();
    }
    
    /**
     * Sets the current state based on activity status
     * @param activity The activity to get state from
     */
    public void setState(Activity activity) {
        if (activity == null || activity.getStatus() == null) {
            this.state = new UpcomingState();
            return;
        }
        
        switch (activity.getStatus()) {
            case UPCOMING:
                this.state = new UpcomingState();
                break;
            case COMPLETED:
                this.state = new CompletedState();
                break;
            case CANCELLED:
                this.state = new CancelledState();
                break;
            default:
                this.state = new UpcomingState();
        }
    }
    
    /**
     * Sets the state directly
     * @param state The state to set
     */
    public void setState(ActivityState state) {
        this.state = state;
    }
    
    /**
     * Publishes the activity
     * @param activity The activity to publish
     * @return Updated activity
     */
    public Activity publish(Activity activity) {
        setState(activity);
        return state.publish(activity);
    }
    
    /**
     * Completes the activity
     * @param activity The activity to complete
     * @return Updated activity
     */
    public Activity complete(Activity activity) {
        setState(activity);
        return state.complete(activity);
    }
    
    /**
     * Cancels the activity
     * @param activity The activity to cancel
     * @return Updated activity
     */
    public Activity cancel(Activity activity) {
        setState(activity);
        return state.cancel(activity);
    }
}
