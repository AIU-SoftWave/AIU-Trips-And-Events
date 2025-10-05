package com.aiu.trips.controller;

import com.aiu.trips.model.Ticket;
import com.aiu.trips.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/generate")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<Ticket> generateTicket(@RequestParam Long bookingId) {
        return ResponseEntity.ok(ticketService.generateTicket(bookingId));
    }

    @PostMapping("/validate")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<Ticket> validateTicket(
            @RequestParam String ticketCode,
            @RequestParam String validatedBy) {
        return ResponseEntity.ok(ticketService.validateTicket(ticketCode, validatedBy));
    }

    @GetMapping("/{ticketCode}")
    public ResponseEntity<Ticket> getTicketByCode(@PathVariable String ticketCode) {
        return ResponseEntity.ok(ticketService.getTicketByCode(ticketCode));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Ticket> getTicketByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(ticketService.getTicketByBooking(bookingId));
    }

    @GetMapping("/{ticketCode}/validity")
    public ResponseEntity<Map<String, Boolean>> checkTicketValidity(@PathVariable String ticketCode) {
        boolean isValid = ticketService.checkTicketValidity(ticketCode);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isValid", isValid);
        return ResponseEntity.ok(response);
    }
}
