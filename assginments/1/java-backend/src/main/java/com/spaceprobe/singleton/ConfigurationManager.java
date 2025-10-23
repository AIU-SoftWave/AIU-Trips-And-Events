package com.spaceprobe.singleton;

import com.spaceprobe.prototype.IPrototype;
import java.util.HashMap;
import java.util.Map;

/**
 * ConfigurationManager implemented as a Singleton.
 * This class manages a registry of prototype SpaceProbe templates.
 * Only one instance of this class can exist in the application.
 */
public class ConfigurationManager {
    // Singleton instance (eagerly initialized for thread-safety)
    private static final ConfigurationManager instance = new ConfigurationManager();
    
    // Registry to store prototypes
    private final Map<String, IPrototype> prototypes;

    /**
     * Private constructor prevents external instantiation.
     * This enforces the Singleton pattern.
     */
    private ConfigurationManager() {
        this.prototypes = new HashMap<>();
        System.out.println("ConfigurationManager instance created (Singleton)");
    }

    /**
     * Public static method to access the single instance.
     * This is the only way to get the ConfigurationManager instance.
     * 
     * @return The single ConfigurationManager instance
     */
    public static ConfigurationManager getInstance() {
        return instance;
    }

    /**
     * Adds a prototype to the registry.
     * 
     * @param key The identifier for the prototype
     * @param prototype The prototype object to store
     */
    public void addPrototype(String key, IPrototype prototype) {
        prototypes.put(key, prototype);
        System.out.println("Prototype '" + key + "' registered in ConfigurationManager");
    }

    /**
     * Retrieves a clone of the specified prototype.
     * 
     * @param key The identifier of the prototype to clone
     * @return A deep clone of the prototype, or null if not found
     */
    public IPrototype getClone(String key) {
        IPrototype prototype = prototypes.get(key);
        if (prototype != null) {
            System.out.println("Cloning prototype '" + key + "'");
            return prototype.deepClone();
        } else {
            System.out.println("Prototype '" + key + "' not found");
            return null;
        }
    }

    /**
     * Returns the number of registered prototypes.
     * 
     * @return The count of prototypes in the registry
     */
    public int getPrototypeCount() {
        return prototypes.size();
    }

    /**
     * Lists all registered prototype keys.
     */
    public void listPrototypes() {
        System.out.println("Registered Prototypes: " + prototypes.keySet());
    }
}
