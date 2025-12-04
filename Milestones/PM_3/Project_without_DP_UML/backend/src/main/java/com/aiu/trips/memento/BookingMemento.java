package com.aiu.trips.memento;

import com.aiu.trips.enums.BookingStatus;
import com.aiu.trips.enums.PaymentMethod;

import java.time.LocalDateTime;

/**
 * Memento Pattern - Memento for Booking state
 * Stores the state of a Booking at a point in time
 */
public class BookingMemento {
    
    private final Long id;
    private final Long userId;
    private final Long eventId;
    private final String bookingCode;
    private final BookingStatus status;
    private final Double amountPaid;
    private final PaymentMethod paymentMethod;
    private final LocalDateTime savedAt;
    
    public BookingMemento(Long id, Long userId, Long eventId, String bookingCode,
                         BookingStatus status, Double amountPaid, PaymentMethod paymentMethod) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.bookingCode = bookingCode;
        this.status = status;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.savedAt = LocalDateTime.now();
    }
    
    // Getters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getEventId() { return eventId; }
    public String getBookingCode() { return bookingCode; }
    public BookingStatus getStatus() { return status; }
    public Double getAmountPaid() { return amountPaid; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public LocalDateTime getSavedAt() { return savedAt; }
}
