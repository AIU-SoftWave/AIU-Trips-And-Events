package com.aiu.trips.chain;

/**
 * Chain of Responsibility Pattern - Abstract handler for request processing
 * Defines the interface for handling requests and chaining handlers
 */
public abstract class RequestHandler {
    
    protected RequestHandler nextHandler;
    
    public void setNext(RequestHandler handler) {
        this.nextHandler = handler;
    }
    
    public abstract Object handle(Object request);
    
    protected Object passToNext(Object request) {
        if (nextHandler != null) {
            return nextHandler.handle(request);
        }
        return request;
    }
}
