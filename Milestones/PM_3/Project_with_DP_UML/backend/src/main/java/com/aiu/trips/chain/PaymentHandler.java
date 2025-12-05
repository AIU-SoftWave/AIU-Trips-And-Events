package com.aiu.trips.chain;

import com.aiu.trips.dto.BookingDTO;
import com.aiu.trips.enums.PaymentMethod;
import org.springframework.stereotype.Component;

/**
 * Concrete Handler: Payment Processing
 * Handles payment for booking
 */
@Component
public class PaymentHandler extends BookingHandler {
    private PaymentMethod method;
    
    public PaymentHandler() {
        this.method = PaymentMethod.CASH;
    }
    
    public PaymentHandler(PaymentMethod method) {
        this.method = method;
    }
    
    @Override
    public void handle(BookingDTO booking) {
        // Process payment based on method
        
        // Pass to next handler
        if (next != null) {
            next.handle(booking);
        }
    }
    
    public void setMethod(PaymentMethod method) {
        this.method = method;
    }
}
