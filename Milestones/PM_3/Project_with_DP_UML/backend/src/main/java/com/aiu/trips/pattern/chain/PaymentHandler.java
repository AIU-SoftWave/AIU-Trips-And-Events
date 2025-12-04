package com.aiu.trips.pattern.chain;

import com.aiu.trips.dto.BookingDTO;
import com.aiu.trips.enums.PaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for processing payment
 */
public class PaymentHandler extends BookingHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentHandler.class);
    private PaymentMethod method;
    
    public PaymentHandler(PaymentMethod method) {
        this.method = method;
    }
    
    @Override
    public void handle(BookingDTO booking) {
        logger.debug("PaymentHandler: Processing payment with method: {}", method);
        
        // In real implementation, process payment based on method
        // For now, assume payment successful and pass to next handler
        
        if (next != null) {
            next.handle(booking);
        }
    }
}
