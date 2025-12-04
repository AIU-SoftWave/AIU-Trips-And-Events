package com.aiu.trips.chain;

import com.aiu.trips.model.Booking;
import org.springframework.stereotype.Component;

/**
 * Chain of Responsibility Pattern - Eligibility handler for bookings
 * Checks if the user is eligible to book the event
 */
@Component
public class EligibilityHandler extends BookingHandler {
    
    @Override
    public boolean handle(Booking booking) {
        // Check user eligibility (e.g., student status, age, etc.)
        System.out.println("EligibilityHandler: Checking user eligibility");
        
        // Pass to next handler
        return passToNext(booking);
    }
}
