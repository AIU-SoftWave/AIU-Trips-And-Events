package com.aiu.trips.chain;

import com.aiu.trips.dto.BookingDTO;

/**
 * Chain of Responsibility for Booking Validation
 * Base handler for booking validation chain
 */
public abstract class BookingHandler {
    protected BookingHandler next;
    
    /**
     * Sets the next handler in the chain
     * @param handler Next handler
     * @return The next handler for method chaining
     */
    public BookingHandler setNext(BookingHandler handler) {
        this.next = handler;
        return handler;
    }
    
    /**
     * Handles the booking validation
     * @param booking Booking to validate
     */
    public abstract void handle(BookingDTO booking);
}
