package com.aiu.trips.pattern.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Early bird pricing - applies discount if booked before deadline
 */
public class EarlyBirdPricingStrategy implements PricingStrategy {
    
    private LocalDateTime earlyBirdDeadline;
    private BigDecimal discountPercent; // e.g., 0.15 for 15% off
    
    public EarlyBirdPricingStrategy(LocalDateTime earlyBirdDeadline, BigDecimal discountPercent) {
        this.earlyBirdDeadline = earlyBirdDeadline;
        this.discountPercent = discountPercent;
    }
    
    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice, LocalDateTime bookingDate, int quantity) {
        if (basePrice == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalPrice = basePrice.multiply(BigDecimal.valueOf(quantity));
        
        // Apply discount if booking is before deadline
        if (bookingDate != null && earlyBirdDeadline != null && bookingDate.isBefore(earlyBirdDeadline)) {
            BigDecimal discount = totalPrice.multiply(discountPercent);
            totalPrice = totalPrice.subtract(discount);
        }
        
        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }
    
    public LocalDateTime getEarlyBirdDeadline() {
        return earlyBirdDeadline;
    }
    
    public void setEarlyBirdDeadline(LocalDateTime earlyBirdDeadline) {
        this.earlyBirdDeadline = earlyBirdDeadline;
    }
    
    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }
    
    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }
}
