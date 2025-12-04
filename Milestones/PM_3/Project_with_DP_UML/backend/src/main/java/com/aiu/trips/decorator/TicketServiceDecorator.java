package com.aiu.trips.decorator;

import com.aiu.trips.model.Booking;

/**
 * Decorator Pattern - Abstract decorator for ticket service
 * Allows adding additional functionality to ticket services
 */
public abstract class TicketServiceDecorator implements ITicketService {
    
    protected ITicketService wrappedService;
    
    public TicketServiceDecorator(ITicketService ticketService) {
        this.wrappedService = ticketService;
    }
    
    @Override
    public String generateTicket(Booking booking) {
        return wrappedService.generateTicket(booking);
    }
    
    @Override
    public boolean validateQRCode(String qrCode) {
        return wrappedService.validateQRCode(qrCode);
    }
}
