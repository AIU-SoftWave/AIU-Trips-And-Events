package com.aiu.trips.bridge;

/**
 * Bridge Pattern - Reminder notification message
 */
public class ReminderMessage extends NotificationMessage {
    
    private String eventTitle;
    private String reminderTime;
    
    public ReminderMessage(NotificationChannel channel, String eventTitle, String reminderTime) {
        super(channel);
        this.eventTitle = eventTitle;
        this.reminderTime = reminderTime;
    }
    
    @Override
    public void sendNotification(String recipient) {
        String message = "Reminder: " + eventTitle + " starts at " + reminderTime;
        channel.send(recipient, message);
    }
}
