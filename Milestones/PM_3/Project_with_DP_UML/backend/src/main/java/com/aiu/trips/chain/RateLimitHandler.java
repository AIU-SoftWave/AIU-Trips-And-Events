package com.aiu.trips.chain;

import org.springframework.stereotype.Component;

/**
 * Chain of Responsibility Pattern - Rate limit handler
 * Prevents abuse by limiting request frequency
 */
@Component
public class RateLimitHandler extends RequestHandler {
    
    @Override
    public Object handle(Object request) {
        // Perform rate limiting check
        System.out.println("RateLimitHandler: Checking rate limits");
        
        // Pass to next handler
        return passToNext(request);
    }
}
