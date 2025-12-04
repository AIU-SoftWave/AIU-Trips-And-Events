package com.aiu.trips.chain;

import com.aiu.trips.model.Booking;

/**
 * Chain of Responsibility Pattern - Abstract handler for booking validation
 */
public abstract class BookingHandler {
    
    protected BookingHandler nextHandler;
    
    public void setNext(BookingHandler handler) {
        this.nextHandler = handler;
    }
    
    public abstract boolean handle(Booking booking);
    
    protected boolean passToNext(Booking booking) {
        if (nextHandler != null) {
            return nextHandler.handle(booking);
        }
        return true;
    }
}
