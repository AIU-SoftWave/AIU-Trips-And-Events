package com.aiu.trips.service;

import com.aiu.trips.constants.AppConstants;
import com.aiu.trips.enums.EventStatus;
import com.aiu.trips.exception.ResourceNotFoundException;
import com.aiu.trips.model.Event;
import com.aiu.trips.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OptimizedEventService - Performance-Critical Component
 * 
 * Low-Latency Design Patterns Implemented:
 * 1. CACHING PATTERN: Uses Caffeine in-memory cache for sub-millisecond reads
 * 2. QUERY OPTIMIZATION: Leverages indexed queries for fast database access
 * 3. READ-THROUGH CACHING: Automatic cache population on miss
 * 4. CACHE INVALIDATION: Smart eviction on updates/deletes
 * 
 * Performance Target: P95 latency < 200ms @ 100 RPS
 * 
 * Key Optimizations:
 * - @Cacheable on read operations (getAllEvents, getUpcomingEvents)
 * - @CacheEvict on write operations to maintain consistency
 * - @Transactional(readOnly=true) for read-only operations
 * - Indexed database queries via repository methods
 */
@Service
public class OptimizedEventService {

    @Autowired
    private EventRepository eventRepository;

    /**
     * Get all active events with caching
     * Cache: "events", TTL: 5 minutes
     * Expected latency: < 10ms (cached), < 50ms (cache miss)
     */
    @Cacheable(value = "events", unless = "#result == null || #result.isEmpty()")
    @Transactional(readOnly = true)
    public List<Event> getAllEvents() {
        return eventRepository.findByStatusNot(EventStatus.CANCELLED);
    }

    /**
     * Get upcoming events with caching
     * Cache: "upcomingEvents", TTL: 5 minutes
     * Expected latency: < 10ms (cached), < 50ms (cache miss)
     */
    @Cacheable(value = "upcomingEvents", unless = "#result == null || #result.isEmpty()")
    @Transactional(readOnly = true)
    public List<Event> getUpcomingEvents() {
        return eventRepository.findByStartDateAfterAndStatus(
            LocalDateTime.now(), 
            EventStatus.ACTIVE
        );
    }

    /**
     * Get event by ID with caching
     * Cache: "eventById", Key: event ID
     * Expected latency: < 5ms (cached), < 30ms (cache miss)
     */
    @Cacheable(value = "eventById", key = "#id", unless = "#result == null")
    @Transactional(readOnly = true)
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EVENT_NOT_FOUND + id));
    }

    /**
     * Update cache when event is modified
     * Evicts all related caches to ensure consistency
     */
    @CacheEvict(value = {"events", "upcomingEvents", "eventById"}, allEntries = true)
    @Transactional
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EVENT_NOT_FOUND + id));
        
        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setType(eventDetails.getType());
        event.setStartDate(eventDetails.getStartDate());
        event.setEndDate(eventDetails.getEndDate());
        event.setLocation(eventDetails.getLocation());
        event.setPrice(eventDetails.getPrice());
        event.setCapacity(eventDetails.getCapacity());
        event.setImageUrl(eventDetails.getImageUrl());
        
        return eventRepository.save(event);
    }

    /**
     * Evict cache when event is deleted
     */
    @CacheEvict(value = {"events", "upcomingEvents", "eventById"}, allEntries = true)
    @Transactional
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.EVENT_NOT_FOUND + id));
        
        event.setStatus(EventStatus.CANCELLED);
        eventRepository.save(event);
    }

    /**
     * Create event and invalidate caches
     */
    @CacheEvict(value = {"events", "upcomingEvents"}, allEntries = true)
    @Transactional
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
}
