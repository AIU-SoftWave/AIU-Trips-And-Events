package com.aiu.trips.factory;

import java.util.List;

/**
 * Model Factory Pattern Interface
 * Open-Closed principle: Factory can be extended with new models without modification
 */
public interface IModelFactory {
    /**
     * Registers a model with a key
     * @param key Model key
     * @param model Model instance
     * @param <T> Model type
     */
    <T> void register(String key, IBaseModel<T> model);
    
    /**
     * Gets a model by key
     * @param key Model key
     * @param <T> Model type
     * @return Model instance
     */
    <T> IBaseModel<T> get(String key);
    
    /**
     * Gets a model by type
     * @param type Model class type
     * @param <T> Model type
     * @return Model instance
     */
    <T> IBaseModel<T> getByType(Class<T> type);
    
    /**
     * Checks if factory contains a model
     * @param key Model key
     * @return true if model exists
     */
    boolean contains(String key);
    
    /**
     * Gets all registered keys
     * @return List of keys
     */
    List<String> keys();
}
