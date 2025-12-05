package com.aiu.trips.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Concrete Strategy: Early Bird Pricing
 * Applies discount for early bookings
 */
@Component
public class EarlyBirdPricingStrategy implements PricingStrategy {
    private LocalDateTime earlyBirdDeadline;
    private BigDecimal discountPercent;
    
    public EarlyBirdPricingStrategy() {
        // Default: 10% discount if booked 30 days in advance
        this.discountPercent = new BigDecimal("0.10");
    }
    
    public void setEarlyBirdDeadline(LocalDateTime deadline) {
        this.earlyBirdDeadline = deadline;
    }
    
    public void setDiscountPercent(BigDecimal percent) {
        this.discountPercent = percent;
    }
    
    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice, LocalDateTime bookingDate, int quantity) {
        BigDecimal totalPrice = basePrice.multiply(BigDecimal.valueOf(quantity));
        
        // Check if booking qualifies for early bird discount
        if (earlyBirdDeadline != null && bookingDate.isBefore(earlyBirdDeadline)) {
            BigDecimal discount = totalPrice.multiply(discountPercent);
            return totalPrice.subtract(discount);
        }
        
        return totalPrice;
    }
}
