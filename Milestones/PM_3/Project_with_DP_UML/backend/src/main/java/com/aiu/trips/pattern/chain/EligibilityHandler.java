package com.aiu.trips.pattern.chain;

import com.aiu.trips.dto.BookingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for checking user eligibility to book
 */
public class EligibilityHandler extends BookingHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(EligibilityHandler.class);
    
    @Override
    public void handle(BookingDTO booking) {
        logger.debug("EligibilityHandler: Checking user eligibility");
        
        // In real implementation, check if user is eligible (e.g., student status, not banned, etc.)
        // For now, assume eligible and pass to next handler
        
        if (next != null) {
            next.handle(booking);
        }
    }
}
