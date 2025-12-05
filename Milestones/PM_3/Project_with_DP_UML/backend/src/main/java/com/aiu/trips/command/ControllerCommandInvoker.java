package com.aiu.trips.command;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Command Pattern Invoker
 * Manages and executes controller commands
 */
@Component
public class ControllerCommandInvoker {
    private final Map<String, IControllerCommand> commandMap = new HashMap<>();
    
    /**
     * Registers a command with a key
     * @param commandName Command name/key
     * @param command Command instance
     */
    public void register(String commandName, IControllerCommand command) {
        commandMap.put(commandName, command);
    }
    
    /**
     * Invokes a command by name
     * @param commandName Command name
     * @param request HTTP request
     * @return Response entity
     */
    public ResponseEntity<?> invoke(String commandName, HttpServletRequest request) {
        IControllerCommand command = commandMap.get(commandName);
        if (command == null) {
            return ResponseEntity.badRequest().body("Command not found: " + commandName);
        }
        return command.execute(request);
    }
    
    /**
     * Checks if a command exists
     * @param commandName Command name
     * @return true if command exists
     */
    public boolean hasCommand(String commandName) {
        return commandMap.containsKey(commandName);
    }
}
