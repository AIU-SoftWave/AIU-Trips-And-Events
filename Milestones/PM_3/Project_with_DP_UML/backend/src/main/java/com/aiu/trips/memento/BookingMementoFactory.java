package com.aiu.trips.memento;

import com.aiu.trips.model.Booking;
import org.springframework.stereotype.Component;

/**
 * Memento Pattern - Factory for creating Booking mementos
 * Based on After DP Data_Layer diagram
 */
@Component
public class BookingMementoFactory {
    
    public com.aiu.trips.model.BookingMemento createFromBooking(Booking booking) {
        return new com.aiu.trips.model.BookingMemento(
            booking.getId(),
            booking.getUser() != null ? booking.getUser().getId() : null,
            // Note: Booking needs to be updated to reference Activity instead of Event
            booking.getEvent() != null ? booking.getEvent().getActivityId() : null,
            booking.getStatus()
        );
    }
    
    public void restoreFromMemento(Booking booking, com.aiu.trips.model.BookingMemento memento) {
        booking.setStatus(memento.getStatus());
    }
}
