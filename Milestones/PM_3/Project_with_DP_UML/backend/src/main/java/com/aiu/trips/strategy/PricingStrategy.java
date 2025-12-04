package com.aiu.trips.strategy;

import com.aiu.trips.model.Event;

/**
 * Strategy Pattern - Interface for pricing strategies
 * Allows dynamic pricing based on different business rules
 */
public interface PricingStrategy {
    Double calculatePrice(Event event, Integer numberOfTickets);
}
