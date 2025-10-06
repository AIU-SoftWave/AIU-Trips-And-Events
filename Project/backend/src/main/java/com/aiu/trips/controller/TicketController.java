package com.aiu.trips.controller;

import com.aiu.trips.dto.TicketDTO;
import com.aiu.trips.model.Ticket;
import com.aiu.trips.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/generate/{bookingId}")
    public ResponseEntity<TicketDTO> generateTicket(@PathVariable Long bookingId) {
        Ticket ticket = ticketService.generateTicket(bookingId);
        return ResponseEntity.ok(convertToDTO(ticket));
    }

    @GetMapping("/validate/{ticketNumber}")
    public ResponseEntity<Boolean> validateTicket(@PathVariable String ticketNumber) {
        boolean isValid = ticketService.validateTicket(ticketNumber);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<TicketDTO> getTicketByBooking(@PathVariable Long bookingId) {
        Ticket ticket = ticketService.getTicketByBooking(bookingId);
        return ResponseEntity.ok(convertToDTO(ticket));
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> cancelTicket(@PathVariable Long ticketId) {
        ticketService.cancelTicket(ticketId);
        return ResponseEntity.noContent().build();
    }

    private TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setTicketNumber(ticket.getTicketNumber());
        dto.setQrCode(ticket.getQrCode());
        dto.setIssueDate(ticket.getIssueDate());
        dto.setValidUntil(ticket.getValidUntil());
        dto.setValid(ticket.isValid());
        
        if (ticket.getBooking() != null) {
            dto.setBookingId(ticket.getBooking().getId());
            dto.setBookingCode(ticket.getBooking().getBookingCode());
            
            if (ticket.getBooking().getEvent() != null) {
                dto.setEventTitle(ticket.getBooking().getEvent().getTitle());
            }
            
            if (ticket.getBooking().getUser() != null) {
                dto.setUserName(ticket.getBooking().getUser().getFullName());
            }
        }
        
        return dto;
    }
}
