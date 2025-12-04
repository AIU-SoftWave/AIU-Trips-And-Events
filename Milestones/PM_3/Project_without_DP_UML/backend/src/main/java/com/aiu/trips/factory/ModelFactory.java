package com.aiu.trips.factory;

import com.aiu.trips.model.*;
import org.springframework.stereotype.Component;

/**
 * Factory Pattern - Concrete implementation for creating model objects
 * Centralizes the creation logic for UserModel, EventModel, BookingModel, 
 * NotificationModel, FeedbackModel
 */
@Component
public class ModelFactory implements IModelFactory {
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T createModel(Class<T> modelClass) {
        if (modelClass == User.class) {
            return (T) new User();
        } else if (modelClass == Event.class) {
            return (T) new Event();
        } else if (modelClass == Booking.class) {
            return (T) new Booking();
        } else if (modelClass == Notification.class) {
            return (T) new Notification();
        } else if (modelClass == Feedback.class) {
            return (T) new Feedback();
        }
        
        throw new IllegalArgumentException("Unknown model class: " + modelClass.getName());
    }
}
