package com.aiu.trips.chain;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Chain of Responsibility Pattern - Abstract Handler
 * Base class for request handlers
 */
public abstract class RequestHandler {
    protected RequestHandler next;
    
    /**
     * Sets the next handler in the chain
     * @param handler Next handler
     * @return The next handler for method chaining
     */
    public RequestHandler setNext(RequestHandler handler) {
        this.next = handler;
        return handler;
    }
    
    /**
     * Handles the request
     * @param request HTTP request
     */
    public abstract void handle(HttpServletRequest request);
}
