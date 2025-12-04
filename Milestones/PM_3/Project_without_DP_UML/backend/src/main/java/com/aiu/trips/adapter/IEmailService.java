package com.aiu.trips.adapter;

/**
 * Adapter Pattern - Interface for email service
 * Defines the contract for sending emails
 */
public interface IEmailService {
    void sendEmail(String to, String subject, String body);
}
