package com.aiu.trips.memento;

import com.aiu.trips.model.Booking;
import org.springframework.stereotype.Component;

/**
 * Memento Pattern - Factory for creating Booking mementos
 */
@Component
public class BookingMementoFactory {
    
    public BookingMemento createMemento(Booking booking) {
        return new BookingMemento(
            booking.getId(),
            booking.getUser().getId(),
            booking.getEvent().getId(),
            booking.getBookingCode(),
            booking.getStatus(),
            booking.getAmountPaid(),
            booking.getPaymentMethod()
        );
    }
    
    public void restoreFromMemento(Booking booking, BookingMemento memento) {
        booking.setBookingCode(memento.getBookingCode());
        booking.setStatus(memento.getStatus());
        booking.setAmountPaid(memento.getAmountPaid());
        booking.setPaymentMethod(memento.getPaymentMethod());
    }
}
