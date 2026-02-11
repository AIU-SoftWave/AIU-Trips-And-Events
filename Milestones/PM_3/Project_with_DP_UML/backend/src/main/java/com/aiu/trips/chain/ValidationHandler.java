package com.aiu.trips.chain;

import org.springframework.stereotype.Component;

/**
 * Chain of Responsibility Pattern - Validation handler
 * Validates request data and business rules
 */
@Component
public class ValidationHandler extends RequestHandler {
    
    @Override
    public Object handle(Object request) {
        // Perform validation
        System.out.println("ValidationHandler: Validating request data");
        
        // Pass to next handler
        return passToNext(request);
    }
}
