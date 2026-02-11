package com.aiu.trips.strategy;

import com.aiu.trips.model.Event;
import org.springframework.stereotype.Component;

/**
 * Strategy Pattern - Bulk/Group discount pricing strategy
 * Applies discount for group bookings
 */
@Component
public class BulkGroupDiscountStrategy implements PricingStrategy {
    
    private static final double GROUP_DISCOUNT_5_PLUS = 0.10; // 10% for 5+ tickets
    private static final double GROUP_DISCOUNT_10_PLUS = 0.20; // 20% for 10+ tickets
    
    @Override
    public Double calculatePrice(Event event, Integer numberOfTickets) {
        double basePrice = event.getPrice() * numberOfTickets;
        
        if (numberOfTickets >= 10) {
            return basePrice * (1 - GROUP_DISCOUNT_10_PLUS);
        } else if (numberOfTickets >= 5) {
            return basePrice * (1 - GROUP_DISCOUNT_5_PLUS);
        }
        
        return basePrice;
    }
}
