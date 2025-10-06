package com.aiu.trips.service.interfaces;

import com.aiu.trips.model.Booking;

import java.util.List;

public interface IBookingService {
    Booking createBooking(Long eventId, String userEmail);
    boolean cancelBooking(Long bookingId, String userEmail);
    Booking getBookingById(Long bookingId);
    List<Booking> getUserBookings(String userEmail);
    Booking confirmBooking(Long bookingId);
    int checkAvailability(Long eventId);
}
