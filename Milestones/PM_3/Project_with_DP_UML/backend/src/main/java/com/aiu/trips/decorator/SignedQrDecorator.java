package com.aiu.trips.decorator;

import com.aiu.trips.model.Booking;
import org.springframework.stereotype.Component;

/**
 * Decorator Pattern - Adds digital signature to QR codes
 * Enhances security by signing tickets
 */
@Component
public class SignedQrDecorator extends TicketServiceDecorator {
    
    public SignedQrDecorator(ITicketService ticketService) {
        super(ticketService);
        if (ticketService == null) {
            throw new IllegalArgumentException("TicketService cannot be null");
        }
    }
    
    @Override
    public String generateTicket(Booking booking) {
        String baseTicket = super.generateTicket(booking);
        // Add digital signature
        return baseTicket + ":SIGNED:" + generateSignature(baseTicket);
    }
    
    @Override
    public boolean validateQRCode(String qrCode) {
        if (qrCode == null || !qrCode.contains(":SIGNED:")) {
            return false;
        }
        
        String[] parts = qrCode.split(":SIGNED:");
        if (parts.length != 2) {
            return false;
        }
        
        String ticket = parts[0];
        String signature = parts[1];
        
        // Verify signature
        return signature.equals(generateSignature(ticket)) && super.validateQRCode(ticket);
    }
    
    private String generateSignature(String data) {
        // Simple signature generation (in production, use proper cryptographic signing)
        return Integer.toHexString(data.hashCode());
    }
}
