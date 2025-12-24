package com.aiu.trips.controller;

import com.aiu.trips.model.Event;
import com.aiu.trips.service.OptimizedEventService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PerformanceOptimizedEventController
 * 
 * Performance-Critical Component for Load Testing
 * Target: P95 latency < 200ms @ 100 RPS
 * 
 * Endpoints optimized with:
 * - Caching layer (Caffeine)
 * - @Timed metrics for Prometheus monitoring
 * - Non-blocking I/O (Spring WebFlux compatible)
 * - Database query optimization
 * - Connection pooling
 */
@RestController
@RequestMapping("/api/v2/events")
public class PerformanceOptimizedEventController {

    @Autowired
    private OptimizedEventService eventService;

    /**
     * GET /api/v2/events
     * Performance target: P95 < 50ms with caching
     */
    @GetMapping
    @Timed(value = "events.getAll", description = "Time taken to retrieve all events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    /**
     * GET /api/v2/events/upcoming
     * Performance target: P95 < 50ms with caching
     */
    @GetMapping("/upcoming")
    @Timed(value = "events.getUpcoming", description = "Time taken to retrieve upcoming events")
    public ResponseEntity<List<Event>> getUpcomingEvents() {
        List<Event> events = eventService.getUpcomingEvents();
        return ResponseEntity.ok(events);
    }

    /**
     * GET /api/v2/events/{id}
     * Performance target: P95 < 30ms with caching
     */
    @GetMapping("/{id}")
    @Timed(value = "events.getById", description = "Time taken to retrieve event by ID")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    /**
     * POST /api/v2/events
     * Performance target: P95 < 150ms
     */
    @PostMapping
    @Timed(value = "events.create", description = "Time taken to create event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event created = eventService.createEvent(event);
        return ResponseEntity.ok(created);
    }

    /**
     * PUT /api/v2/events/{id}
     * Performance target: P95 < 150ms
     */
    @PutMapping("/{id}")
    @Timed(value = "events.update", description = "Time taken to update event")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event updated = eventService.updateEvent(id, event);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/v2/events/{id}
     * Performance target: P95 < 100ms
     */
    @DeleteMapping("/{id}")
    @Timed(value = "events.delete", description = "Time taken to delete event")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
