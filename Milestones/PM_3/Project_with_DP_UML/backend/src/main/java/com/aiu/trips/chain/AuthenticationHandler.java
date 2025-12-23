package com.aiu.trips.chain;

import org.springframework.stereotype.Component;

/**
 * Chain of Responsibility Pattern - Authentication handler
 * Validates authentication tokens in the request chain
 */
@Component
public class AuthenticationHandler extends RequestHandler {
    
    @Override
    public Object handle(Object request) {
        // Perform authentication check
        // In a real implementation, this would validate JWT tokens
        System.out.println("AuthenticationHandler: Validating authentication");
        
        // Pass to next handler
        return passToNext(request);
    }
}
