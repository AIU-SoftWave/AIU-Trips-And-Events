package com.aiu.trips.controller;

import com.aiu.trips.command.*;
import com.aiu.trips.service.interfaces.IBookingTicketingSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * BookingController - Uses Command Pattern for all operations
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private ControllerCommandInvoker commandInvoker;

    @Autowired
    private IBookingTicketingSystem bookingService;

    @GetMapping("/browse")
    public ResponseEntity<?> browseEvents() {
        IControllerCommand command = new BrowseEventsCommand(bookingService);
        commandInvoker.pushToQueue(command);
        return commandInvoker.executeNext(new HashMap<>());
    }

    @PostMapping("/event/{eventId}")
    public ResponseEntity<?> createBooking(@PathVariable Long eventId, Authentication authentication) {
        Map<String, Object> data = new HashMap<>();
        data.put("eventId", eventId);
        // Get user ID from authentication (simplified - would need actual user lookup)
        data.put("studentId", 1L);
        
        IControllerCommand command = new BookEventCommand(bookingService);
        commandInvoker.pushToQueue(command);
        return commandInvoker.executeNext(data);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateTicket(@RequestBody Map<String, Object> requestData) {
        IControllerCommand command = new ValidateTicketCommand(bookingService);
        commandInvoker.pushToQueue(command);
        return commandInvoker.executeNext(requestData);
    }
}
