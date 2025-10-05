package com.aiu.trips.service;

import com.aiu.trips.model.Ticket;
import com.aiu.trips.model.Booking;
import com.aiu.trips.repository.TicketRepository;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.exception.ResourceNotFoundException;
import com.aiu.trips.util.QRCodeGenerator;
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
    
    @Autowired
    private NotificationService notificationService;
    
    @Transactional
    public Ticket generateTicket(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        
        // Check if ticket already exists
        return ticketRepository.findByBooking(booking).orElseGet(() -> {
            Ticket ticket = new Ticket();
            ticket.setBooking(booking);
            ticket.setTicketCode(generateTicketCode());
            
            // Generate QR code with booking information
            String qrData = String.format("BOOKING:%s|EVENT:%s|USER:%s", 
                booking.getBookingCode(), 
                booking.getEvent().getId(), 
                booking.getUser().getEmail());
            
            String qrCodePath;
            try {
                qrCodePath = qrCodeGenerator.generateQRCodeBase64(qrData);
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate QR code", e);
            }
            ticket.setQrCode(qrCodePath);
            
            Ticket savedTicket = ticketRepository.save(ticket);
            
            // Send ticket via email
            notificationService.sendTicketEmail(booking.getUser(), booking, savedTicket);
            
            return savedTicket;
        });
    }
    
    @Transactional
    public Ticket validateTicket(String ticketCode, String validatedBy) {
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        
        if (ticket.isValidated()) {
            throw new IllegalStateException("Ticket has already been validated");
        }
        
        ticket.setValidated(true);
        ticket.setValidatedAt(LocalDateTime.now());
        ticket.setValidatedBy(validatedBy);
        
        return ticketRepository.save(ticket);
    }
    
    public Ticket getTicketByCode(String ticketCode) {
        return ticketRepository.findByTicketCode(ticketCode)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
    }
    
    public Ticket getTicketByBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return ticketRepository.findByBooking(booking)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found for this booking"));
    }
    
    public boolean checkTicketValidity(String ticketCode) {
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        return !ticket.isValidated();
    }
    
    private String generateTicketCode() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }
}
