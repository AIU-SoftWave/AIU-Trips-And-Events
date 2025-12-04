package com.aiu.trips.bridge;

/**
 * Bridge Pattern - Interface for notification channels
 * Defines how notifications are delivered
 */
public interface NotificationChannel {
    void send(String recipient, String message);
}
