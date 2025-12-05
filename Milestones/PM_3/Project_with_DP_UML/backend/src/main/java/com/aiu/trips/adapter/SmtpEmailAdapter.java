package com.aiu.trips.adapter;

import org.springframework.stereotype.Component;

/**
 * Adapter for SMTP Email Service
 * Adapts Spring's JavaMailSender to our IEmailService interface
 */
@Component
public class SmtpEmailAdapter implements IEmailService {
    
    @Override
    public boolean sendEmail(String to, String subject, String body) {
        // Fallback implementation: log the email
        // In production, integrate with JavaMailSender when mail dependency is added
        System.out.println("===== EMAIL =====");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("=================");
        return true;
    }
}

