package com.aiu.trips.decorator;

import com.aiu.trips.model.Booking;

/**
 * Decorator Pattern - Interface for ticket service
 * Defines the base operations for ticket generation and validation
 */
public interface ITicketService {
    String generateTicket(Booking booking);
    boolean validateQRCode(String qrCode);
}
