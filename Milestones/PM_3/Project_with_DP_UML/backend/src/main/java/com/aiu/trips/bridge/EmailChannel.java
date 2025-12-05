package com.aiu.trips.bridge;

import com.aiu.trips.adapter.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Concrete Bridge Implementation: Email Channel
 * Sends notifications via email
 */
@Component
public class EmailChannel implements NotificationChannel {
    
    @Autowired(required = false)
    private IEmailService emailService;
    
    @Override
    public boolean send(String recipient, String content) {
        if (emailService != null) {
            return emailService.sendEmail(recipient, "Notification", content);
        }
        // Fallback: log the notification
        System.out.println("Email to " + recipient + ": " + content);
        return true;
    }
}
