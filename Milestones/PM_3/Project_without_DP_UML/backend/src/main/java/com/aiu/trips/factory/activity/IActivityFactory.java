package com.aiu.trips.factory.activity;

import com.aiu.trips.model.Event;

/**
 * Abstract Factory Pattern - Interface for creating Activity (Event/Trip) objects
 * Provides an abstraction for creating different types of activities
 */
public interface IActivityFactory {
    Event createActivity();
    Event clonePrototype(Event prototype);
}
