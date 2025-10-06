package com.aiu.trips.controller;

import com.aiu.trips.model.Ticket;
import com.aiu.trips.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {
    
    @Autowired
    private TicketService ticketService;
    
    /**
     * Generate ticket for a booking
     */
    @PostMapping("/generate/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Ticket> generateTicket(@PathVariable Long bookingId) {
        Ticket ticket = ticketService.generateTicket(bookingId);
        return ResponseEntity.ok(ticket);
    }
    
    /**
     * Get ticket by booking ID
     */
    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Ticket> getTicketByBookingId(@PathVariable Long bookingId) {
        Ticket ticket = ticketService.getTicketByBookingId(bookingId);
        return ResponseEntity.ok(ticket);
    }
    
    /**
     * Get ticket by ticket code
     */
    @GetMapping("/code/{ticketCode}")
    public ResponseEntity<Ticket> getTicketByCode(@PathVariable String ticketCode) {
        Ticket ticket = ticketService.getTicketByCode(ticketCode);
        return ResponseEntity.ok(ticket);
    }
    
    /**
     * Validate ticket (for entry verification)
     */
    @PostMapping("/validate/{ticketCode}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public ResponseEntity<Map<String, Object>> validateTicket(@PathVariable String ticketCode) {
        boolean isValid = ticketService.validateTicket(ticketCode);
        
        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);
        
        if (isValid) {
            response.put("message", "Ticket validated successfully");
            Ticket ticket = ticketService.getTicketByCode(ticketCode);
            response.put("ticket", ticket);
        } else {
            response.put("message", "Invalid or already used ticket");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Check if ticket is valid (without marking as used)
     */
    @GetMapping("/check/{ticketCode}")
    public ResponseEntity<Map<String, Boolean>> checkTicketValidity(@PathVariable String ticketCode) {
        boolean isValid = ticketService.isTicketValid(ticketCode);
        return ResponseEntity.ok(Map.of("valid", isValid));
    }
}
