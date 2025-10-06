package com.aiu.trips.service;

import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Ticket;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.TicketRepository;
import com.aiu.trips.util.QRCodeGenerator;
import com.aiu.trips.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private QRCodeGenerator qrCodeGenerator;
    
    /**
     * Generate a ticket for a booking
     */
    @Transactional
    public Ticket generateTicket(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        
        // Check if ticket already exists
        if (ticketRepository.existsByBookingId(bookingId)) {
            return ticketRepository.findByBookingId(bookingId).get();
        }
        
        Ticket ticket = new Ticket();
        ticket.setBooking(booking);
        
        // Generate unique ticket code
        String ticketCode = UUID.randomUUID().toString();
        ticket.setTicketCode(ticketCode);
        
        // Generate QR code data
        String qrData = generateQRData(booking, ticketCode);
        ticket.setQrCodeData(qrData);
        
        return ticketRepository.save(ticket);
    }
    
    /**
     * Validate a ticket by ticket code
     */
    @Transactional
    public boolean validateTicket(String ticketCode) {
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        
        if (ticket.getIsUsed()) {
            return false; // Ticket already used
        }
        
        // Mark ticket as used
        ticket.setIsUsed(true);
        ticket.setUsedAt(LocalDateTime.now());
        ticketRepository.save(ticket);
        
        return true;
    }
    
    /**
     * Get ticket by ticket code
     */
    public Ticket getTicketByCode(String ticketCode) {
        return ticketRepository.findByTicketCode(ticketCode)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
    }
    
    /**
     * Get ticket by booking ID
     */
    public Ticket getTicketByBookingId(Long bookingId) {
        return ticketRepository.findByBookingId(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found for booking"));
    }
    
    /**
     * Check if ticket is valid (exists and not used)
     */
    public boolean isTicketValid(String ticketCode) {
        return ticketRepository.findByTicketCode(ticketCode)
            .map(ticket -> !ticket.getIsUsed())
            .orElse(false);
    }
    
    /**
     * Generate QR code data string
     */
    private String generateQRData(Booking booking, String ticketCode) {
        return String.format("TICKET:%s|EVENT:%d|BOOKING:%d|STUDENT:%d",
            ticketCode,
            booking.getEvent().getId(),
            booking.getId(),
            booking.getUser().getId());
    }
}
