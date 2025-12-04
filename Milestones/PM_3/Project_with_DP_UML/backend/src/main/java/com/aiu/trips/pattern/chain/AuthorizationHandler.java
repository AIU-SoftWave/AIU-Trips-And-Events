package com.aiu.trips.pattern.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for authorization in the request chain
 */
public class AuthorizationHandler extends RequestHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationHandler.class);
    
    @Override
    public void handle(Object request) {
        // Simplified authorization check
        logger.debug("AuthorizationHandler: Checking user permissions");
        
        // In real implementation, check user roles, permissions, etc.
        // For now, just pass to next handler
        
        if (next != null) {
            next.handle(request);
        }
    }
}
