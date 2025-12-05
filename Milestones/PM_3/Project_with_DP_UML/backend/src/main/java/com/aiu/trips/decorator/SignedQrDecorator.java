package com.aiu.trips.decorator;

import org.springframework.stereotype.Component;

/**
 * Concrete Decorator: Signed QR Code
 * Adds cryptographic signing to tickets
 */
@Component
public class SignedQrDecorator extends TicketServiceDecorator {
    private String secretKey;
    
    public SignedQrDecorator(ITicketService service) {
        super(service);
        this.secretKey = "default-secret-key";
    }
    
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    
    @Override
    public String generateTicket(Long bookingId) {
        String basicTicket = super.generateTicket(bookingId);
        // Add signature
        String signature = generateSignature(basicTicket);
        return basicTicket + "|SIG:" + signature;
    }
    
    @Override
    public boolean validateQRCode(String qrCode) {
        if (qrCode == null || !qrCode.contains("|SIG:")) {
            return false;
        }
        
        String[] parts = qrCode.split("\\|SIG:");
        if (parts.length != 2) {
            return false;
        }
        
        String ticket = parts[0];
        String providedSignature = parts[1];
        String expectedSignature = generateSignature(ticket);
        
        return expectedSignature.equals(providedSignature) && super.validateQRCode(ticket);
    }
    
    private String generateSignature(String data) {
        // Simple hash-based signature (in production, use proper crypto)
        return String.valueOf((data + secretKey).hashCode());
    }
}
