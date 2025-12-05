package com.aiu.trips.chain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Concrete Handler: Authentication
 * Validates user authentication in the chain
 */
@Component
public class AuthenticationHandler extends RequestHandler {
    
    @Override
    public void handle(HttpServletRequest request) {
        // Authentication logic would go here
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Valid token - pass to next handler
            if (next != null) {
                next.handle(request);
            }
        } else {
            // Invalid - throw exception or handle error
            throw new RuntimeException("Authentication failed");
        }
    }
}
