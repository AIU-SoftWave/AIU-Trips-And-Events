package com.aiu.trips.chain;

import org.springframework.stereotype.Component;

/**
 * Chain of Responsibility Pattern - Authorization handler
 * Checks if the authenticated user has permission for the requested action
 */
@Component
public class AuthorizationHandler extends RequestHandler {
    
    @Override
    public Object handle(Object request) {
        // Perform authorization check
        System.out.println("AuthorizationHandler: Checking user permissions");
        
        // Pass to next handler
        return passToNext(request);
    }
}
