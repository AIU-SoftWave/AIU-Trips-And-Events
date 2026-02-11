package com.aiu.trips.bridge;

import org.springframework.stereotype.Component;

/**
 * Bridge Pattern - In-app channel implementation
 * Sends notifications within the application
 */
@Component
public class InAppChannel implements NotificationChannel {
    
    @Override
    public void send(String recipient, String message) {
        System.out.println("Sending IN-APP notification to " + recipient + ": " + message);
        // In production, save to notification table
    }
}
