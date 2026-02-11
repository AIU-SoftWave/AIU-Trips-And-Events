package com.aiu.trips.decorator;

import com.aiu.trips.model.Booking;
import org.springframework.stereotype.Component;

/**
 * Decorator Pattern - Base implementation of ticket service
 * Provides basic ticket generation functionality
 */
@Component
public class BaseTicketService implements ITicketService {
    
    @Override
    public String generateTicket(Booking booking) {
        // Basic ticket generation
        return "TICKET-" + booking.getBookingCode();
    }
    
    @Override
    public boolean validateQRCode(String qrCode) {
        // Basic QR code validation
        return qrCode != null && qrCode.startsWith("TICKET-");
    }
}
