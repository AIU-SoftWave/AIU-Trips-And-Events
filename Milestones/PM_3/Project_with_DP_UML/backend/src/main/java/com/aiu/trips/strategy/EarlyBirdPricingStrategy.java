package com.aiu.trips.strategy;

import com.aiu.trips.model.Event;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Strategy Pattern - Early bird pricing strategy
 * Applies discount for bookings made well in advance
 */
@Component
public class EarlyBirdPricingStrategy implements PricingStrategy {
    
    private static final double EARLY_BIRD_DISCOUNT = 0.15; // 15% discount
    private static final long EARLY_BIRD_DAYS_THRESHOLD = 30; // 30 days before event
    
    @Override
    public Double calculatePrice(Event event, Integer numberOfTickets) {
        double basePrice = event.getPrice() * numberOfTickets;
        
        LocalDateTime now = LocalDateTime.now();
        long daysUntilEvent = ChronoUnit.DAYS.between(now, event.getStartDate());
        
        if (daysUntilEvent >= EARLY_BIRD_DAYS_THRESHOLD) {
            return basePrice * (1 - EARLY_BIRD_DISCOUNT);
        }
        
        return basePrice;
    }
}
