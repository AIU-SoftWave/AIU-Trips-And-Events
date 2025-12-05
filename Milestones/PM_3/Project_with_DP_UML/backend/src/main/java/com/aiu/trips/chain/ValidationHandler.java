package com.aiu.trips.chain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Concrete Handler: Validation
 * Validates request data in the chain
 */
@Component
public class ValidationHandler extends RequestHandler {
    
    @Override
    public void handle(HttpServletRequest request) {
        // Validation logic would go here
        // Validate request parameters
        
        // Pass to next handler
        if (next != null) {
            next.handle(request);
        }
    }
}
