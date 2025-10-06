package com.aiu.trips.service;

import com.aiu.trips.constants.AppConstants;
import com.aiu.trips.exception.ResourceNotFoundException;
import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Ticket;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.TicketRepository;
import com.aiu.trips.service.interfaces.ITicketService;
import com.aiu.trips.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TicketService implements ITicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @Override
    @Transactional
    public Ticket generateTicket(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.BOOKING_NOT_FOUND + bookingId));

        // Check if ticket already exists for this booking
        if (ticketRepository.findByBooking_Id(bookingId).isPresent()) {
            throw new RuntimeException("Ticket already exists for this booking");
        }

        Ticket ticket = new Ticket();
        ticket.setBooking(booking);
        ticket.setTicketNumber(UUID.randomUUID().toString());
        
        // Set valid until to event end date
        ticket.setValidUntil(booking.getEvent().getEndDate() != null 
            ? booking.getEvent().getEndDate() 
            : booking.getEvent().getStartDate().plusDays(1));

        // Generate QR code
        try {
            String qrData = "TICKET:" + ticket.getTicketNumber() + "|BOOKING:" + booking.getBookingCode();
            String qrCode = qrCodeGenerator.generateQRCodeBase64(qrData);
            ticket.setQrCode(qrCode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code: " + e.getMessage());
        }

        return ticketRepository.save(ticket);
    }

    @Override
    public boolean validateTicket(String ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found: " + ticketNumber));

        if (!ticket.isValid()) {
            return false;
        }

        if (ticket.getValidUntil() != null && LocalDateTime.now().isAfter(ticket.getValidUntil())) {
            return false;
        }

        return true;
    }

    @Override
    public Ticket getTicketByBooking(Long bookingId) {
        return ticketRepository.findByBooking_Id(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found for booking: " + bookingId));
    }

    @Override
    @Transactional
    public boolean cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found: " + ticketId));

        ticket.setValid(false);
        ticketRepository.save(ticket);
        return true;
    }
}
