package com.aiu.trips.chain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Concrete Handler: Authorization
 * Validates user permissions in the chain
 */
@Component
public class AuthorizationHandler extends RequestHandler {
    
    @Override
    public void handle(HttpServletRequest request) {
        // Authorization logic would go here
        // Check user roles and permissions
        
        // Pass to next handler
        if (next != null) {
            next.handle(request);
        }
    }
}
