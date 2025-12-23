package com.aiu.trips.command;

import com.aiu.trips.service.BookingService;

/**
 * Command Pattern - Concrete command for booking an event
 */
public class BookEventCommand implements IControllerCommand {
    
    private final BookingService bookingService;
    private final Long eventId;
    private final String userEmail;
    
    public BookEventCommand(BookingService bookingService, Long eventId, String userEmail) {
        this.bookingService = bookingService;
        this.eventId = eventId;
        this.userEmail = userEmail;
    }
    
    @Override
    public Object execute() {
        return bookingService.createBooking(eventId, userEmail);
    }
}
