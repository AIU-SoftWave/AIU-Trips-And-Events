package com.aiu.trips.memento;

import com.aiu.trips.factory.IModelFactory;
import com.aiu.trips.model.Booking;
import com.aiu.trips.model.BookingMemento;
import com.aiu.trips.repository.BookingMementoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Memento Pattern Caretaker for Booking
 * Manages booking history snapshots
 */
@Component
public class BookingHistoryCaretaker {
    
    @Autowired
    private BookingMementoRepository mementoRepository;
    
    @Autowired
    private IModelFactory modelFactory;
    
    /**
     * Saves a snapshot of booking state
     * @param booking Booking to snapshot
     */
    public void save(Booking booking) {
        BookingMemento memento = new BookingMemento();
        memento.setBookingId(booking.getId());
        memento.setStudentId(booking.getUser() != null ? booking.getUser().getId() : null);
        memento.setActivityId(booking.getActivity() != null ? booking.getActivity().getActivityId() : null);
        memento.setStatus(booking.getStatus());
        
        mementoRepository.save(memento);
    }
    
    /**
     * Gets the last memento for a booking
     * @param bookingId Booking ID
     * @return Last memento or null
     */
    public BookingMemento getLastMemento(Long bookingId) {
        return mementoRepository
            .findFirstByBookingIdOrderBySnapshotDateDesc(bookingId)
            .orElse(null);
    }
    
    /**
     * Restores booking from memento
     * @param booking Booking to restore
     * @param memento Memento to restore from
     */
    public void restore(Booking booking, BookingMemento memento) {
        if (memento != null) {
            booking.setStatus(memento.getStatus());
        }
    }
}
