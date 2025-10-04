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

import java.time.LocalDateTime;
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
    
    @Autowired
    private EmailService emailService;

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
        
        // Check registration deadline
        if (event.getRegistrationDeadline() != null && LocalDateTime.now().isAfter(event.getRegistrationDeadline())) {
            throw new RuntimeException("Registration deadline has passed");
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
        booking.setStatus("PENDING_PAYMENT");
        booking.setPaymentStatus("PENDING");

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
            "Booking created for: " + event.getTitle() + ". Please complete payment.",
            "INFO"
        );

        return savedBooking;
    }
    
    @Transactional
    public Booking processPayment(Long bookingId, String paymentMethod, String transactionId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        if (!"PENDING_PAYMENT".equals(booking.getStatus())) {
            throw new RuntimeException("Booking is not in pending payment state");
        }
        
        // Simulate payment processing
        // In production, integrate with actual payment gateway (Stripe, PayPal, etc.)
        booking.setPaymentStatus("COMPLETED");
        booking.setPaymentMethod(paymentMethod);
        booking.setTransactionId(transactionId);
        booking.setPaymentDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");
        
        Booking confirmedBooking = bookingRepository.save(booking);
        
        // Send confirmation email with ticket
        emailService.sendBookingConfirmation(
            booking.getUser().getEmail(), 
            booking.getBookingCode(), 
            booking.getEvent().getTitle(),
            booking.getQrCodePath()
        );
        
        booking.setTicketSent(true);
        bookingRepository.save(booking);
        
        // Send notification
        notificationService.notifyUser(
            booking.getUser().getId(),
            "Payment confirmed for: " + booking.getEvent().getTitle() + ". Ticket sent to your email.",
            "SUCCESS"
        );
        
        return confirmedBooking;
    }
    
    @Transactional
    public Booking validateQRCodeAndMarkAttendance(String bookingCode) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode)
            .orElseThrow(() -> new RuntimeException("Invalid booking code"));
        
        if (!"CONFIRMED".equals(booking.getStatus())) {
            throw new RuntimeException("Booking is not confirmed");
        }
        
        if (booking.getAttended() != null && booking.getAttended()) {
            throw new RuntimeException("Already marked as attended");
        }
        
        booking.setAttended(true);
        booking.setAttendedAt(LocalDateTime.now());
        booking.setStatus("ATTENDED");
        
        return bookingRepository.save(booking);
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
        
        // Process refund if payment was made
        if ("COMPLETED".equals(booking.getPaymentStatus())) {
            booking.setPaymentStatus("REFUNDED");
            // TODO: Integrate with payment gateway for actual refund
        }
        
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
