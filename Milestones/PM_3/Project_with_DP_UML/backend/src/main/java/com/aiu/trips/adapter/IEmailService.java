package com.aiu.trips.adapter;

/**
 * Adapter Pattern Interface for Email Service
 * Target interface that our system expects
 */
public interface IEmailService {
    /**
     * Sends an email
     * @param to Recipient email
     * @param subject Email subject
     * @param body Email body
     * @return Success status
     */
    boolean sendEmail(String to, String subject, String body);
}
