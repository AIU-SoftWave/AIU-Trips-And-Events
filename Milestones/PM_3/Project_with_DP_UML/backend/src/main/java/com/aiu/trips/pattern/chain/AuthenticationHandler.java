package com.aiu.trips.pattern.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for authentication in the request chain
 */
public class AuthenticationHandler extends RequestHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);
    
    @Override
    public void handle(Object request) {
        // Simplified authentication check
        logger.debug("AuthenticationHandler: Validating authentication");
        
        // In real implementation, check JWT token, session, etc.
        // For now, just pass to next handler
        
        if (next != null) {
            next.handle(request);
        }
    }
}
