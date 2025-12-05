package com.aiu.trips.factory;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of Model Factory
 * Maintains a registry of model instances
 */
@Component
public class ModelFactory implements IModelFactory {
    private final Map<String, IBaseModel<?>> registry = new HashMap<>();
    private final Map<Class<?>, IBaseModel<?>> typeRegistry = new HashMap<>();
    
    @Override
    public <T> void register(String key, IBaseModel<T> model) {
        registry.put(key, model);
    }
    
    /**
     * Registers a model by both key and type
     * @param key Model key
     * @param type Model type class
     * @param model Model instance
     * @param <T> Model type
     */
    public <T> void register(String key, Class<T> type, IBaseModel<T> model) {
        registry.put(key, model);
        typeRegistry.put(type, model);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> IBaseModel<T> get(String key) {
        return (IBaseModel<T>) registry.get(key);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> IBaseModel<T> getByType(Class<T> type) {
        return (IBaseModel<T>) typeRegistry.get(type);
    }
    
    @Override
    public boolean contains(String key) {
        return registry.containsKey(key);
    }
    
    @Override
    public List<String> keys() {
        return new ArrayList<>(registry.keySet());
    }
}
