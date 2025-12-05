package com.aiu.trips.command;

import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Command Pattern Interface
 * Encapsulates a request as an object
 */
public interface IControllerCommand {
    /**
     * Executes the command
     * @param request HTTP request
     * @return Response entity
     */
    ResponseEntity<?> execute(HttpServletRequest request);
}
