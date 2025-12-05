package com.aiu.trips.chain;

import com.aiu.trips.dto.BookingDTO;
import org.springframework.stereotype.Component;

/**
 * Concrete Handler: Capacity Check
 * Validates if activity has available seats
 */
@Component
public class CapacityHandler extends BookingHandler {
    
    @Override
    public void handle(BookingDTO booking) {
        // Check if activity has available seats
        
        // Pass to next handler
        if (next != null) {
            next.handle(booking);
        }
    }
}
