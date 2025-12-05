package com.aiu.trips.prototype;

/**
 * Prototype Pattern Interface
 * Allows objects to be cloned without coupling to their concrete classes
 */
public interface IPrototype<T> {
    /**
     * Creates and returns a copy of this object
     * @return A clone of this instance
     */
    T clone();
}
