package com.aiu.trips.chain;

import com.aiu.trips.dto.BookingDTO;
import org.springframework.stereotype.Component;

/**
 * Concrete Handler: Eligibility Check
 * Validates if user is eligible to book
 */
@Component
public class EligibilityHandler extends BookingHandler {
    
    @Override
    public void handle(BookingDTO booking) {
        // Check if user is eligible to book
        // e.g., check if user is a student, has no pending bookings, etc.
        
        // Pass to next handler
        if (next != null) {
            next.handle(booking);
        }
    }
}
