package com.aiu.trips.controller;

import com.aiu.trips.model.Booking;
import com.aiu.trips.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Booking> createBooking(@PathVariable Long eventId, Authentication authentication) {
        return ResponseEntity.ok(bookingService.createBooking(eventId, authentication.getName()));
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId, Authentication authentication) {
        bookingService.cancelBooking(bookingId, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<Booking>> getMyBookings(Authentication authentication) {
        return ResponseEntity.ok(bookingService.getUserBookings(authentication.getName()));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Booking>> getEventBookings(@PathVariable Long eventId) {
        return ResponseEntity.ok(bookingService.getEventBookings(eventId));
    }

    @GetMapping("/code/{bookingCode}")
    public ResponseEntity<Booking> getBookingByCode(@PathVariable String bookingCode) {
        return ResponseEntity.ok(bookingService.getBookingByCode(bookingCode));
    }

    @PostMapping("/validate/{bookingCode}")
    public ResponseEntity<Booking> validateTicket(@PathVariable String bookingCode, Authentication authentication) {
        return ResponseEntity.ok(bookingService.validateTicket(bookingCode, authentication.getName()));
    }
}
