package com.aiu.trips.bridge;

/**
 * Bridge Pattern - New event notification message
 */
public class NewEventMessage extends NotificationMessage {
    
    private String eventTitle;
    
    public NewEventMessage(NotificationChannel channel, String eventTitle) {
        super(channel);
        this.eventTitle = eventTitle;
    }
    
    @Override
    public void sendNotification(String recipient) {
        String message = "New Event Available: " + eventTitle;
        channel.send(recipient, message);
    }
}
