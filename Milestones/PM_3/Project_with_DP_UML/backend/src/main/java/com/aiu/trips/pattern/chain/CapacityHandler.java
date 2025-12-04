package com.aiu.trips.pattern.chain;

import com.aiu.trips.dto.BookingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for checking event capacity
 */
public class CapacityHandler extends BookingHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(CapacityHandler.class);
    
    @Override
    public void handle(BookingDTO booking) {
        logger.debug("CapacityHandler: Checking event capacity");
        
        // In real implementation, check if event has available seats
        // For now, assume capacity available and pass to next handler
        
        if (next != null) {
            next.handle(booking);
        }
    }
}
