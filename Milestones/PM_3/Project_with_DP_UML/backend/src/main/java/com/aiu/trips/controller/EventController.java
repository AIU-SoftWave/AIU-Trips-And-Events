package com.aiu.trips.controller;

import com.aiu.trips.enums.ActivityType;
import com.aiu.trips.model.Activity;
import com.aiu.trips.model.Event;
import com.aiu.trips.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Activity>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Activity>> getEventsByType(@PathVariable ActivityType type) {
        return ResponseEntity.ok(eventService.getEventsByType(type));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Activity>> getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }

    @PostMapping
    public ResponseEntity<Activity> createEvent(@RequestBody Event event, Authentication authentication) {
        return ResponseEntity.ok(eventService.createEvent(event, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<Activity>> getMyEvents(Authentication authentication) {
        return ResponseEntity.ok(eventService.getEventsByUser(authentication.getName()));
    }
}
