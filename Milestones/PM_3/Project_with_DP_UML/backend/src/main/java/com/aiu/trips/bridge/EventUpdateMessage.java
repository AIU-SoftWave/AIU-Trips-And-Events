package com.aiu.trips.bridge;

/**
 * Bridge Pattern - Event update notification message
 */
public class EventUpdateMessage extends NotificationMessage {
    
    private String eventTitle;
    private String updateDetails;
    
    public EventUpdateMessage(NotificationChannel channel, String eventTitle, String updateDetails) {
        super(channel);
        this.eventTitle = eventTitle;
        this.updateDetails = updateDetails;
    }
    
    @Override
    public void sendNotification(String recipient) {
        String message = "Event Update - " + eventTitle + ": " + updateDetails;
        channel.send(recipient, message);
    }
}
