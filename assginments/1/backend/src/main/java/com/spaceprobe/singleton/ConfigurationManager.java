package com.spaceprobe.singleton;

import com.spaceprobe.prototype.IPrototype;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Singleton Pattern Implementation
 * Manages prototype instances in a registry
 * Thread-safe implementation using eager initialization
 */
public class ConfigurationManager {
    // Singleton instance (eagerly initialized for thread safety)
    private static final ConfigurationManager instance = new ConfigurationManager();
    
    // Registry to hold prototypes
    private final Map<String, IPrototype> prototypes;
    
    /**
     * Private constructor prevents external instantiation
     */
    private ConfigurationManager() {
        this.prototypes = new HashMap<>();
    }
    
    /**
     * Public static method to access the singleton instance
     * @return The single instance of ConfigurationManager
     */
    public static ConfigurationManager getInstance() {
        return instance;
    }
    
    /**
     * Adds a prototype to the registry
     * @param key The identifier for the prototype
     * @param prototype The prototype to store
     */
    public void addPrototype(String key, IPrototype prototype) {
        if (key == null || prototype == null) {
            throw new IllegalArgumentException("Key and prototype must not be null");
        }
        prototypes.put(key, prototype);
    }
    
    /**
     * Retrieves a clone of the prototype with the given key
     * @param key The identifier of the prototype
     * @return A deep clone of the prototype
     */
    public IPrototype getClone(String key) {
        IPrototype prototype = prototypes.get(key);
        if (prototype == null) {
            throw new IllegalArgumentException("No prototype found with key: " + key);
        }
        return prototype.deepClone();
    }
    
    /**
     * Checks if a prototype exists with the given key
     * @param key The identifier to check
     * @return true if the prototype exists, false otherwise
     */
    public boolean hasPrototype(String key) {
        return prototypes.containsKey(key);
    }
    
    /**
     * Gets all registered prototype keys
     * @return Set of all prototype keys
     */
    public Set<String> getPrototypeKeys() {
        return prototypes.keySet();
    }
    
    /**
     * Clears all prototypes from the registry
     */
    public void clearPrototypes() {
        prototypes.clear();
    }
}
