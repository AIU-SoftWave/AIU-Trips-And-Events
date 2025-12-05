package com.aiu.trips.factory;

import com.aiu.trips.dto.ActivityDTO;
import com.aiu.trips.model.Activity;

/**
 * Abstract Factory Pattern Interface
 * Factory for creating Activity objects (Event/Trip)
 */
public interface IActivityFactory {
    /**
     * Creates an activity from data transfer object
     * @param data Activity data
     * @return Created Activity instance
     */
    Activity createActivity(ActivityDTO data);
    
    /**
     * Clones an existing activity using prototype pattern
     * @param activity Activity to clone
     * @return Cloned Activity instance
     */
    Activity clonePrototype(Activity activity);
}
