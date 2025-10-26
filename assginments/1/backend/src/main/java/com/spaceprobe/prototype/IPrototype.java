package com.spaceprobe.prototype;

/**
 * Prototype Pattern Interface
 * Supports efficient deep copying of objects
 */
public interface IPrototype {
    /**
     * Creates a deep clone of the object
     * @return A new instance with copied values
     */
    IPrototype deepClone();
}
