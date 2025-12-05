package com.aiu.trips.strategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Strategy Pattern Interface for Pricing
 * Defines pricing calculation strategy
 */
public interface PricingStrategy {
    /**
     * Calculates price based on strategy
     * @param basePrice Base price
     * @param bookingDate Booking date
     * @param quantity Number of bookings
     * @return Calculated price
     */
    BigDecimal calculatePrice(BigDecimal basePrice, LocalDateTime bookingDate, int quantity);
}
