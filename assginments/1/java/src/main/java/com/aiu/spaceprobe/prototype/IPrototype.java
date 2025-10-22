package com.aiu.spaceprobe.prototype;

/**
 * Prototype interface for supporting efficient deep copying
 */
public interface IPrototype {
    /**
     * Creates a deep clone of the object
     * @return A deep copy of the object
     */
    IPrototype deepClone();
}
