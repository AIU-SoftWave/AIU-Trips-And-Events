package com.aiu.trips.service.interfaces;

import com.aiu.trips.model.Event;

import java.util.List;

public interface IEventService {
    Event createEvent(Event event);
    Event updateEvent(Long eventId, Event eventData);
    boolean deleteEvent(Long eventId);
    Event getEventById(Long eventId);
    List<Event> getAllEvents();
    List<Event> searchEvents(String keyword);
}
