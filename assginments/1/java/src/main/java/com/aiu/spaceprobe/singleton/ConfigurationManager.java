package com.aiu.spaceprobe.singleton;

import com.aiu.spaceprobe.prototype.IPrototype;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class that manages prototype instances
 * Implements the Singleton pattern to ensure only one instance exists
 */
public class ConfigurationManager {
    // Singleton instance
    private static ConfigurationManager instance;
    
    // Registry to store prototypes
    private Map<String, IPrototype> prototypes;

    /**
     * Private constructor to prevent instantiation
     */
    private ConfigurationManager() {
        this.prototypes = new HashMap<>();
    }

    /**
     * Get the singleton instance
     * Thread-safe lazy initialization
     * @return The singleton ConfigurationManager instance
     */
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    /**
     * Add a prototype to the registry
     * @param key The key to identify the prototype
     * @param prototype The prototype to store
     */
    public void addPrototype(String key, IPrototype prototype) {
        prototypes.put(key, prototype);
        System.out.println("✓ Registered prototype: " + key);
    }

    /**
     * Get a clone of a registered prototype
     * @param key The key of the prototype to clone
     * @return A deep clone of the prototype, or null if not found
     */
    public IPrototype getClone(String key) {
        IPrototype prototype = prototypes.get(key);
        if (prototype != null) {
            return prototype.deepClone();
        }
        System.out.println("✗ Prototype not found: " + key);
        return null;
    }

    /**
     * Check if a prototype exists
     * @param key The key to check
     * @return true if the prototype exists
     */
    public boolean hasPrototype(String key) {
        return prototypes.containsKey(key);
    }

    /**
     * Get all registered prototype keys
     * @return Array of all registered keys
     */
    public String[] getRegisteredKeys() {
        return prototypes.keySet().toArray(new String[0]);
    }
}
