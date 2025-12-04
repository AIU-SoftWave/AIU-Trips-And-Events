package com.aiu.trips.command;

import com.aiu.trips.model.Event;
import com.aiu.trips.service.EventService;

/**
 * Command Pattern - Concrete command for creating an event
 */
public class CreateEventCommand implements IControllerCommand {
    
    private final EventService eventService;
    private final Event event;
    private final String userEmail;
    
    public CreateEventCommand(EventService eventService, Event event, String userEmail) {
        this.eventService = eventService;
        this.event = event;
        this.userEmail = userEmail;
    }
    
    @Override
    public Object execute() {
        return eventService.createEvent(event, userEmail);
    }
}
