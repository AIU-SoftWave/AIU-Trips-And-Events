package com.aiu.trips.adapter;

import org.springframework.stereotype.Component;

/**
 * Adapter Pattern - SMTP email adapter
 * Adapts third-party email service (e.g., JavaMailSender) to our interface
 * In production, this would wrap JavaMailSender or similar email service
 */
@Component
public class SmtpEmailAdapter implements IEmailService {
    
    // In production, inject JavaMailSender or similar service
    // @Autowired
    // private JavaMailSender mailSender;
    
    @Override
    public void sendEmail(String to, String subject, String body) {
        // Adapt to third-party email service
        System.out.println("SMTP Email Adapter: Sending email");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        
        // In production:
        // MimeMessage message = mailSender.createMimeMessage();
        // MimeMessageHelper helper = new MimeMessageHelper(message);
        // helper.setTo(to);
        // helper.setSubject(subject);
        // helper.setText(body);
        // mailSender.send(message);
    }
}
