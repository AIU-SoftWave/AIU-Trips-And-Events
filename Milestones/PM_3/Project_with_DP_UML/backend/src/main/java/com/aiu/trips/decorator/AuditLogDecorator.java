package com.aiu.trips.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Concrete Decorator: Audit Logging
 * Adds logging functionality to ticket operations
 */
@Component
public class AuditLogDecorator extends TicketServiceDecorator {
    private static final Logger logger = LoggerFactory.getLogger(AuditLogDecorator.class);
    
    public AuditLogDecorator(ITicketService service) {
        super(service);
    }
    
    @Override
    public String generateTicket(Long bookingId) {
        logger.info("Generating ticket for booking ID: {}", bookingId);
        String ticket = super.generateTicket(bookingId);
        logger.info("Ticket generated successfully: {}", ticket);
        return ticket;
    }
    
    @Override
    public boolean validateQRCode(String qrCode) {
        logger.info("Validating QR code: {}", qrCode);
        boolean isValid = super.validateQRCode(qrCode);
        logger.info("QR code validation result: {}", isValid);
        return isValid;
    }
}
