package com.aiu.trips.state;

import com.aiu.trips.model.Event;

/**
 * State Pattern - Interface for Activity states
 * Defines behavior for different lifecycle states of activities
 */
public interface ActivityState {
    void handleStateTransition(ActivityLifecycle context);
    boolean canBook();
    boolean canEdit();
    boolean canCancel();
    String getStateName();
}
