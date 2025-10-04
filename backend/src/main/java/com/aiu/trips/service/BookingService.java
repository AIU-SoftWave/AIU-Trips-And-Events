package com.aiu.trips.service;

import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.EventRepository;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Booking createBooking(Long eventId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        if (bookingRepository.existsByUser_IdAndEvent_Id(user.getId(), eventId)) {
            throw new RuntimeException("Already booked this event");
        }

        // Update available seats
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);

        // Create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setBookingCode(UUID.randomUUID().toString());
        booking.setAmountPaid(event.getPrice());

        // Generate QR code
        try {
            String qrData = "BOOKING:" + booking.getBookingCode() + "|EVENT:" + event.getId();
            String qrCode = qrCodeGenerator.generateQRCodeBase64(qrData);
            booking.setQrCodePath(qrCode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code", e);
        }

        Booking savedBooking = bookingRepository.save(booking);

        // Send notification
        notificationService.notifyUser(
            user.getId(),
            "Booking confirmed for: " + event.getTitle(),
            "SUCCESS"
        );

        return savedBooking;
    }

    public void cancelBooking(Long bookingId, String userEmail) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to cancel this booking");
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        // Update available seats
        Event event = booking.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + 1);
        eventRepository.save(event);

        // Send notification
        notificationService.notifyUser(
            user.getId(),
            "Booking cancelled for: " + event.getTitle(),
            "INFO"
        );
    }

    public List<Booking> getUserBookings(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return bookingRepository.findByUser_Id(user.getId());
    }

    public List<Booking> getEventBookings(Long eventId) {
        return bookingRepository.findByEvent_Id(eventId);
    }

    public Booking getBookingByCode(String bookingCode) {
        return bookingRepository.findByBookingCode(bookingCode)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
}
