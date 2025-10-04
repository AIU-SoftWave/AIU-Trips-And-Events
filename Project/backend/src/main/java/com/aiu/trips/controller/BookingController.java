package com.aiu.trips.controller;

import com.aiu.trips.model.Booking;
import com.aiu.trips.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Booking> createBooking(@PathVariable Long eventId, Authentication authentication) {
        return ResponseEntity.ok(bookingService.createBooking(eventId, authentication.getName()));
    }
    
    @PostMapping("/{bookingId}/payment")
    public ResponseEntity<Booking> processPayment(
            @PathVariable Long bookingId,
            @RequestBody Map<String, String> paymentDetails) {
        String paymentMethod = paymentDetails.get("paymentMethod");
        String transactionId = paymentDetails.get("transactionId");
        return ResponseEntity.ok(bookingService.processPayment(bookingId, paymentMethod, transactionId));
    }
    
    @PostMapping("/validate-qr/{bookingCode}")
    public ResponseEntity<Booking> validateQRCode(@PathVariable String bookingCode) {
        return ResponseEntity.ok(bookingService.validateQRCodeAndMarkAttendance(bookingCode));
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
}
