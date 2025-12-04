package com.aiu.trips.pattern.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Bulk/group discount pricing - applies discount if quantity meets threshold
 */
public class BulkGroupDiscountStrategy implements PricingStrategy {
    
    private int threshold; // Minimum quantity for discount
    private BigDecimal discountPercent; // e.g., 0.10 for 10% off
    
    public BulkGroupDiscountStrategy(int threshold, BigDecimal discountPercent) {
        this.threshold = threshold;
        this.discountPercent = discountPercent;
    }
    
    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice, LocalDateTime bookingDate, int quantity) {
        if (basePrice == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalPrice = basePrice.multiply(BigDecimal.valueOf(quantity));
        
        // Apply discount if quantity meets or exceeds threshold
        if (quantity >= threshold) {
            BigDecimal discount = totalPrice.multiply(discountPercent);
            totalPrice = totalPrice.subtract(discount);
        }
        
        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }
    
    public int getThreshold() {
        return threshold;
    }
    
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
    
    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }
    
    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }
}
