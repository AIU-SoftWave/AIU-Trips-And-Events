package com.aiu.trips.bridge;

/**
 * Bridge Pattern - Abstract notification message
 * Defines the structure of notification messages
 */
public abstract class NotificationMessage {
    
    protected NotificationChannel channel;
    
    public NotificationMessage(NotificationChannel channel) {
        this.channel = channel;
    }
    
    public abstract void sendNotification(String recipient);
}
