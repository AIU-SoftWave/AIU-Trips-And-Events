package com.aiu.trips.pattern.chain;

import com.aiu.trips.dto.BookingDTO;

/**
 * Abstract handler for booking validation chain
 */
public abstract class BookingHandler {
    
    protected BookingHandler next;
    
    /**
     * Sets the next handler in the chain
     * @param handler The next handler
     * @return The next handler for chaining
     */
    public BookingHandler setNext(BookingHandler handler) {
        this.next = handler;
        return handler;
    }
    
    /**
     * Handles the booking validation or passes to next handler
     * @param booking The booking DTO to validate
     */
    public abstract void handle(BookingDTO booking);
}
