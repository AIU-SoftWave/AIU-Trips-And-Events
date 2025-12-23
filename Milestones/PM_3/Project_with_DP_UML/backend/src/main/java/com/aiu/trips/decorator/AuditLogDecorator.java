package com.aiu.trips.decorator;

import com.aiu.trips.model.Booking;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Decorator Pattern - Adds audit logging to ticket operations
 * Tracks ticket generation and validation for security
 */
@Component
public class AuditLogDecorator extends TicketServiceDecorator {
    
    private Map<String, LocalDateTime> auditLog = new HashMap<>();
    
    public AuditLogDecorator(ITicketService ticketService) {
        super(ticketService);
        if (ticketService == null) {
            throw new IllegalArgumentException("TicketService cannot be null");
        }
    }
    
    @Override
    public String generateTicket(Booking booking) {
        String ticket = super.generateTicket(booking);
        
        // Log ticket generation
        auditLog.put(ticket, LocalDateTime.now());
        System.out.println("AUDIT: Ticket generated for booking: " + booking.getBookingCode() + 
                         " at " + LocalDateTime.now());
        
        return ticket;
    }
    
    @Override
    public boolean validateQRCode(String qrCode) {
        boolean isValid = super.validateQRCode(qrCode);
        
        // Log validation attempt
        System.out.println("AUDIT: QR code validation - Code: " + qrCode + 
                         ", Valid: " + isValid + " at " + LocalDateTime.now());
        
        return isValid;
    }
    
    public Map<String, LocalDateTime> getAuditLog() {
        return new HashMap<>(auditLog);
    }
}
