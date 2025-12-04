package com.aiu.trips.chain;

import com.aiu.trips.model.Booking;
import org.springframework.stereotype.Component;

/**
 * Chain of Responsibility Pattern - Capacity handler for bookings
 * Checks if the event has available capacity
 */
@Component
public class CapacityHandler extends BookingHandler {
    
    @Override
    public boolean handle(Booking booking) {
        // Check event capacity
        System.out.println("CapacityHandler: Checking event capacity");
        
        if (booking.getEvent().getAvailableSeats() <= 0) {
            return false;
        }
        
        // Pass to next handler
        return passToNext(booking);
    }
}
