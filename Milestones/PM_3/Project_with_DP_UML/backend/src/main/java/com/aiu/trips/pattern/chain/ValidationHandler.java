package com.aiu.trips.pattern.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for validation in the request chain
 */
public class ValidationHandler extends RequestHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ValidationHandler.class);
    
    @Override
    public void handle(Object request) {
        // Simplified validation check
        logger.debug("ValidationHandler: Validating request data");
        
        // In real implementation, validate request parameters, body, etc.
        // For now, just pass to next handler
        
        if (next != null) {
            next.handle(request);
        }
    }
}
