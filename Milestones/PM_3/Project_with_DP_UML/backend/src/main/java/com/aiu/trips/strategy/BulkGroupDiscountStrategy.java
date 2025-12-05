package com.aiu.trips.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Concrete Strategy: Bulk/Group Discount Pricing
 * Applies discount for group bookings
 */
@Component
public class BulkGroupDiscountStrategy implements PricingStrategy {
    private int threshold;
    private BigDecimal discountPercent;
    
    public BulkGroupDiscountStrategy() {
        // Default: 15% discount for groups of 10 or more
        this.threshold = 10;
        this.discountPercent = new BigDecimal("0.15");
    }
    
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
    
    public void setDiscountPercent(BigDecimal percent) {
        this.discountPercent = percent;
    }
    
    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice, LocalDateTime bookingDate, int quantity) {
        BigDecimal totalPrice = basePrice.multiply(BigDecimal.valueOf(quantity));
        
        // Apply discount if quantity meets threshold
        if (quantity >= threshold) {
            BigDecimal discount = totalPrice.multiply(discountPercent);
            return totalPrice.subtract(discount);
        }
        
        return totalPrice;
    }
}
