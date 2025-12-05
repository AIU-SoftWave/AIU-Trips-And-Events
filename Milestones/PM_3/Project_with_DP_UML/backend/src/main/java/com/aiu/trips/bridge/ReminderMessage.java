package com.aiu.trips.bridge;

/**
 * Concrete Abstraction: Reminder Message
 */
public class ReminderMessage extends NotificationMessage {
    private String eventName;
    private Integer hoursUntilEvent;
    
    public ReminderMessage(NotificationChannel channel, String eventName, Integer hoursUntilEvent) {
        super(channel);
        this.eventName = eventName;
        this.hoursUntilEvent = hoursUntilEvent;
    }
    
    @Override
    protected String formatContent() {
        return "Reminder: " + eventName + " starts in " + hoursUntilEvent + " hours!";
    }
}
