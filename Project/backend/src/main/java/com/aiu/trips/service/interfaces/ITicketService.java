package com.aiu.trips.service.interfaces;

import com.aiu.trips.model.Ticket;

public interface ITicketService {
    Ticket generateTicket(Long bookingId);
    boolean validateTicket(String ticketNumber);
    Ticket getTicketByBooking(Long bookingId);
    boolean cancelTicket(Long ticketId);
}
