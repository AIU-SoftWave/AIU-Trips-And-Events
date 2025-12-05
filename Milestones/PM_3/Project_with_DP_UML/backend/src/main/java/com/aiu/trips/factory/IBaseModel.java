package com.aiu.trips.factory;

import java.util.List;

/**
 * Model Factory Pattern Interface
 * Generic factory for managing repository models
 * @param <T> Type of model
 */
public interface IBaseModel<T> {
    // Marker interface for repository models
    // Spring Data JPA repositories will implement this conceptually
}
