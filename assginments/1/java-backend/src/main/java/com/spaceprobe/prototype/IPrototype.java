package com.spaceprobe.prototype;

/**
 * Prototype interface for supporting deep cloning of objects.
 * This pattern allows efficient creation of new objects by copying existing ones.
 */
public interface IPrototype {
    /**
     * Creates a deep clone of the current object.
     * All mutable fields should be properly copied to ensure independence.
     * 
     * @return A new independent instance with the same configuration
     */
    IPrototype deepClone();
}
