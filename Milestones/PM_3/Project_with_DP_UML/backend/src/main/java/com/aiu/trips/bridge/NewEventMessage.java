package com.aiu.trips.bridge;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Concrete Abstraction: New Event Message
 */
public class NewEventMessage extends NotificationMessage {
    private String eventName;
    private LocalDateTime eventDate;
    
    public NewEventMessage(NotificationChannel channel, String eventName, LocalDateTime eventDate) {
        super(channel);
        this.eventName = eventName;
        this.eventDate = eventDate;
    }
    
    @Override
    protected String formatContent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        return "New Event: " + eventName + " on " + eventDate.format(formatter);
    }
}
