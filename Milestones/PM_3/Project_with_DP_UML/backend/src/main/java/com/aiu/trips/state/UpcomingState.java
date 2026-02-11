package com.aiu.trips.state;

/**
 * State Pattern - Concrete state for upcoming activities
 */
public class UpcomingState implements ActivityState {
    
    @Override
    public void handleStateTransition(ActivityLifecycle context) {
        // Transition to completed or cancelled based on business logic
    }
    
    @Override
    public boolean canBook() {
        return true;
    }
    
    @Override
    public boolean canEdit() {
        return true;
    }
    
    @Override
    public boolean canCancel() {
        return true;
    }
    
    @Override
    public String getStateName() {
        return "UPCOMING";
    }
}
