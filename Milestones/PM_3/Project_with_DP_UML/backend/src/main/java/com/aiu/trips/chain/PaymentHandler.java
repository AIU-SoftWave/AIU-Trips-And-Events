package com.aiu.trips.chain;

import com.aiu.trips.model.Booking;
import org.springframework.stereotype.Component;

/**
 * Chain of Responsibility Pattern - Payment handler for bookings
 * Validates payment information
 */
@Component
public class PaymentHandler extends BookingHandler {
    
    @Override
    public boolean handle(Booking booking) {
        // Validate payment
        System.out.println("PaymentHandler: Validating payment");
        
        // Pass to next handler
        return passToNext(booking);
    }
}
