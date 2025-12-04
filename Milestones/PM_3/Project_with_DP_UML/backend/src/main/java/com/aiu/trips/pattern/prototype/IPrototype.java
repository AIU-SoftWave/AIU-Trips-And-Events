package com.aiu.trips.pattern.prototype;

/**
 * Prototype pattern interface
 * @param <T> Type of object to be cloned
 */
public interface IPrototype<T> {
    /**
     * Creates and returns a copy of this object
     * @return A clone of this instance
     */
    T clone();
}
