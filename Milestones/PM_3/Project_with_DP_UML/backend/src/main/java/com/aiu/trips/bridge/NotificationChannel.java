package com.aiu.trips.bridge;

/**
 * Bridge Pattern - Implementation Interface
 * Notification Channel interface
 */
public interface NotificationChannel {
    /**
     * Sends notification through the channel
     * @param recipient Recipient identifier
     * @param content Notification content
     * @return Success status
     */
    boolean send(String recipient, String content);
}
