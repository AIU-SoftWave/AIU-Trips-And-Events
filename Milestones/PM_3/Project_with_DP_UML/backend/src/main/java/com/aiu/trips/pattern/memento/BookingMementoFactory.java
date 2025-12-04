package com.aiu.trips.pattern.memento;

import com.aiu.trips.model.Booking;

/**
 * Factory for creating BookingMemento objects from Booking entities
 */
public class BookingMementoFactory {
    
    /**
     * Creates a memento from a Booking instance
     * @param booking The booking to create memento from
     * @return BookingMemento snapshot
     */
    public static BookingMemento createFromBooking(Booking booking) {
        if (booking == null) {
            return null;
        }
        
        BookingMemento memento = new BookingMemento();
        memento.setBookingId(booking.getId());
        memento.setStudentId(booking.getUser() != null ? booking.getUser().getId().toString() : null);
        memento.setActivityId(booking.getEvent() != null ? booking.getEvent().getId() : null);
        memento.setStatus(booking.getStatus());
        
        return memento;
    }
}
