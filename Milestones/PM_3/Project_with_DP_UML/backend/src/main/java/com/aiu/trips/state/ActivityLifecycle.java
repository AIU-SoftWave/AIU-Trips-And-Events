package com.aiu.trips.state;

import com.aiu.trips.model.Event;
import org.springframework.stereotype.Component;

/**
 * State Pattern - Context class for managing activity lifecycle
 * Maintains the current state and delegates state-specific behavior
 */
@Component
public class ActivityLifecycle {
    
    private ActivityState currentState;
    private Event event;
    
    public ActivityLifecycle() {
        this.currentState = new UpcomingState();
    }
    
    public void setState(ActivityState state) {
        this.currentState = state;
    }
    
    public ActivityState getState() {
        return currentState;
    }
    
    public void setEvent(Event event) {
        this.event = event;
    }
    
    public Event getEvent() {
        return event;
    }
    
    public void transitionState() {
        currentState.handleStateTransition(this);
    }
    
    public boolean canBook() {
        return currentState.canBook();
    }
    
    public boolean canEdit() {
        return currentState.canEdit();
    }
    
    public boolean canCancel() {
        return currentState.canCancel();
    }
    
    public String getCurrentStateName() {
        return currentState.getStateName();
    }
}
