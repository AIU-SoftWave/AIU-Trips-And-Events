package com.aiu.trips.strategy;

import com.aiu.trips.model.Event;
import org.springframework.stereotype.Component;

/**
 * Strategy Pattern - Standard pricing strategy
 * Returns the base price without any discounts
 */
@Component
public class StandardPricingStrategy implements PricingStrategy {
    
    @Override
    public Double calculatePrice(Event event, Integer numberOfTickets) {
        return event.getPrice() * numberOfTickets;
    }
}
