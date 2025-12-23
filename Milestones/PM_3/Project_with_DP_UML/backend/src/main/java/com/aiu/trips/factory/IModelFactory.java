package com.aiu.trips.factory;

/**
 * Factory Pattern - Interface for creating model objects
 * Provides a unified interface for creating different types of domain models
 */
public interface IModelFactory {
    <T> T createModel(Class<T> modelClass);
}
