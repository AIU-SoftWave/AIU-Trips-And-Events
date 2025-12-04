package com.aiu.trips.pattern.chain;

/**
 * Abstract handler for Chain of Responsibility pattern
 * Used for processing HTTP requests through a chain of handlers
 */
public abstract class RequestHandler {
    
    protected RequestHandler next;
    
    /**
     * Sets the next handler in the chain
     * @param handler The next handler
     * @return The next handler for chaining
     */
    public RequestHandler setNext(RequestHandler handler) {
        this.next = handler;
        return handler;
    }
    
    /**
     * Handles the request or passes to next handler
     * @param request The HTTP request (simplified as Object for now)
     */
    public abstract void handle(Object request);
}
