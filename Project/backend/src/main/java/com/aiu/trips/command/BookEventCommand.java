package com.aiu.trips.command;

import com.aiu.trips.dto.BookingDTO;
import com.aiu.trips.service.interfaces.IBookingTicketingSystem;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public class BookEventCommand implements IControllerCommand {
    private final IBookingTicketingSystem bookingService;

    public BookEventCommand(IBookingTicketingSystem bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public ResponseEntity<?> execute(Map<String, Object> requestData) {
        try {
            Long studentId = Long.valueOf(requestData.get("studentId").toString());
            Long eventId = Long.valueOf(requestData.get("eventId").toString());
            BookingDTO result = bookingService.bookEvent(studentId, eventId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
