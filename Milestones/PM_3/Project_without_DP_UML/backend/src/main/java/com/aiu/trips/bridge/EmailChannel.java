package com.aiu.trips.bridge;

import org.springframework.stereotype.Component;

/**
 * Bridge Pattern - Email channel implementation
 * Sends notifications via email
 */
@Component
public class EmailChannel implements NotificationChannel {
    
    @Override
    public void send(String recipient, String message) {
        System.out.println("Sending EMAIL to " + recipient + ": " + message);
        // In production, integrate with actual email service
    }
}
