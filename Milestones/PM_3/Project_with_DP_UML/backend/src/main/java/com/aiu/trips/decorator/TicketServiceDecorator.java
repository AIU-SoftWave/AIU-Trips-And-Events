package com.aiu.trips.decorator;

/**
 * Abstract Decorator for Ticket Service
 * Base class for all ticket service decorators
 */
public abstract class TicketServiceDecorator implements ITicketService {
    protected ITicketService wrappedService;
    
    public TicketServiceDecorator(ITicketService service) {
        this.wrappedService = service;
    }
    
    @Override
    public String generateTicket(Long bookingId) {
        return wrappedService.generateTicket(bookingId);
    }
    
    @Override
    public boolean validateQRCode(String qrCode) {
        return wrappedService.validateQRCode(qrCode);
    }
}
