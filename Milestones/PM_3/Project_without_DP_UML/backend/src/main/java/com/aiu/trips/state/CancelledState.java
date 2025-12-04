package com.aiu.trips.state;

/**
 * State Pattern - Concrete state for cancelled activities
 */
public class CancelledState implements ActivityState {
    
    @Override
    public void handleStateTransition(ActivityLifecycle context) {
        // Cancelled is a final state
    }
    
    @Override
    public boolean canBook() {
        return false;
    }
    
    @Override
    public boolean canEdit() {
        return false;
    }
    
    @Override
    public boolean canCancel() {
        return false;
    }
    
    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}
