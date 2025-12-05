package com.aiu.trips.decorator;

import com.aiu.trips.factory.IModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Base Ticket Service Implementation
 * Provides core ticket generation functionality
 */
@Component
public class BaseTicketService implements ITicketService {
    
    @Autowired
    private IModelFactory modelFactory;
    
    @Override
    public String generateTicket(Long bookingId) {
        // Generate basic QR code
        return "TICKET-" + bookingId + "-" + System.currentTimeMillis();
    }
    
    @Override
    public boolean validateQRCode(String qrCode) {
        // Basic validation
        return qrCode != null && qrCode.startsWith("TICKET-");
    }
}
