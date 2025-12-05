package com.aiu.trips.bridge;

/**
 * Bridge Pattern - Abstraction
 * Base class for notification messages
 */
public abstract class NotificationMessage {
    protected NotificationChannel channel;
    
    public NotificationMessage(NotificationChannel channel) {
        this.channel = channel;
    }
    
    /**
     * Sends the notification
     * @param recipient Recipient identifier
     * @return Success status
     */
    public boolean send(String recipient) {
        String content = formatContent();
        return channel.send(recipient, content);
    }
    
    /**
     * Formats the notification content
     * @return Formatted content
     */
    protected abstract String formatContent();
}
