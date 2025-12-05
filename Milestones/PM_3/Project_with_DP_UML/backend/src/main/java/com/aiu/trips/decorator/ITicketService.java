package com.aiu.trips.decorator;

/**
 * Ticket Service Interface for Decorator Pattern
 */
public interface ITicketService {
    /**
     * Generates a ticket for a booking
     * @param bookingId Booking ID
     * @return Ticket data (e.g., QR code)
     */
    String generateTicket(Long bookingId);
    
    /**
     * Validates a QR code
     * @param qrCode QR code to validate
     * @return true if valid
     */
    boolean validateQRCode(String qrCode);
}
