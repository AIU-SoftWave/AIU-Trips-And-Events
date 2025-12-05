package com.aiu.trips.bridge;

import com.aiu.trips.factory.IModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Concrete Bridge Implementation: In-App Channel
 * Stores notifications in database
 */
@Component
public class InAppChannel implements NotificationChannel {
    
    @Autowired
    private IModelFactory modelFactory;
    
    @Override
    public boolean send(String recipient, String content) {
        // Store notification in database
        // In a real implementation, this would save to NotificationRepository
        System.out.println("In-App notification for " + recipient + ": " + content);
        return true;
    }
}
