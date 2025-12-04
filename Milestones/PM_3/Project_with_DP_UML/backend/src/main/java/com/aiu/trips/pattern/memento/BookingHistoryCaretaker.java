package com.aiu.trips.pattern.memento;

import com.aiu.trips.model.Booking;
import com.aiu.trips.repository.BookingMementoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Caretaker for Booking mementos - manages saving and retrieving booking snapshots
 */
@Component
public class BookingHistoryCaretaker {
    
    @Autowired
    private BookingMementoRepository mementoRepository;
    
    /**
     * Saves a snapshot of the booking's current state
     * @param booking The booking to snapshot
     */
    public void save(Booking booking) {
        if (booking == null || booking.getId() == null) {
            return;
        }
        
        BookingMemento memento = BookingMementoFactory.createFromBooking(booking);
        mementoRepository.save(memento);
    }
    
    /**
     * Retrieves the most recent memento for a booking
     * @param bookingId The booking ID
     * @return The last memento, or null if none exists
     */
    public BookingMemento getLastMemento(Long bookingId) {
        return mementoRepository.findFirstByBookingIdOrderBySnapshotDateDesc(bookingId)
                .orElse(null);
    }
}
