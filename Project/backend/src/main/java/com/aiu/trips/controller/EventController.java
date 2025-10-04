package com.aiu.trips.controller;

import com.aiu.trips.model.Event;
import com.aiu.trips.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Event>> getEventsByType(@PathVariable String type) {
        return ResponseEntity.ok(eventService.getEventsByType(type));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Event>> getEventsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(eventService.getEventsByCategory(category));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event, Authentication authentication) {
        return ResponseEntity.ok(eventService.createEvent(event, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<Event>> getMyEvents(Authentication authentication) {
        return ResponseEntity.ok(eventService.getEventsByUser(authentication.getName()));
    }
    
    @PostMapping("/{id}/send-message")
    public ResponseEntity<String> sendMessageToParticipants(
            @PathVariable Long id, 
            @RequestBody Map<String, String> payload,
            Authentication authentication) {
        String message = payload.get("message");
        eventService.sendCustomMessageToParticipants(id, message, authentication.getName());
        return ResponseEntity.ok("Message sent to all participants");
    }
}
