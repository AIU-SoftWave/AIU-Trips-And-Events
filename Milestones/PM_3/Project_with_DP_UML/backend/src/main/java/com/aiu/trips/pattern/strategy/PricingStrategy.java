package com.aiu.trips.pattern.strategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Strategy pattern interface for pricing calculations
 */
public interface PricingStrategy {
    /**
     * Calculates the final price based on strategy
     * @param basePrice The base price
     * @param bookingDate When the booking is made
     * @param quantity Number of tickets/seats
     * @return Calculated price
     */
    BigDecimal calculatePrice(BigDecimal basePrice, LocalDateTime bookingDate, int quantity);
}
