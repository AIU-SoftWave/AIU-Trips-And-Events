package com.aiu.trips.pattern.strategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Standard pricing - no discounts applied
 */
public class StandardPricingStrategy implements PricingStrategy {
    
    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice, LocalDateTime bookingDate, int quantity) {
        if (basePrice == null) {
            return BigDecimal.ZERO;
        }
        return basePrice.multiply(BigDecimal.valueOf(quantity));
    }
}
