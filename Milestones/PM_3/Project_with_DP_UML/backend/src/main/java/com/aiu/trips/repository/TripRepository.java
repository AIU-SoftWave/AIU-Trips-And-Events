package com.aiu.trips.repository;

import com.aiu.trips.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Trip entities
 * Based on After DP diagrams structure
 */
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    // Additional query methods can be added here as needed
}
