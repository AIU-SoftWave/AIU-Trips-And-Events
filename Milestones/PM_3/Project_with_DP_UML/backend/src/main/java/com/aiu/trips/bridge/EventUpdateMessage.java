package com.aiu.trips.bridge;

/**
 * Concrete Abstraction: Event Update Message
 */
public class EventUpdateMessage extends NotificationMessage {
    private String eventName;
    private String updateDetails;
    
    public EventUpdateMessage(NotificationChannel channel, String eventName, String updateDetails) {
        super(channel);
        this.eventName = eventName;
        this.updateDetails = updateDetails;
    }
    
    @Override
    protected String formatContent() {
        return "Event Update for " + eventName + ": " + updateDetails;
    }
}
