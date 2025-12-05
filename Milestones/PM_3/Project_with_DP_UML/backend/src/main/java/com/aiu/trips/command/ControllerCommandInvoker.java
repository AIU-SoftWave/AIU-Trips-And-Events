package com.aiu.trips.command;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Command Pattern Invoker
 * Manages and executes controller commands with queue-based execution
 */
@Component
public class ControllerCommandInvoker {
    private final Map<String, IControllerCommand> commandMap = new HashMap<>();
    private final Queue<CommandExecution> commandQueue = new LinkedList<>();
    
    /**
     * Inner class to hold command and its request for queued execution
     */
    private static class CommandExecution {
        final IControllerCommand command;
        final HttpServletRequest request;
        
        CommandExecution(IControllerCommand command, HttpServletRequest request) {
            this.command = command;
            this.request = request;
        }
    }
    
    /**
     * Registers a command with a key
     * @param commandName Command name/key
     * @param command Command instance
     */
    public void register(String commandName, IControllerCommand command) {
        commandMap.put(commandName, command);
    }
    
    /**
     * Pushes a command to the queue for later execution
     * @param commandName Command name
     * @param request HTTP request
     * @return true if command was successfully queued
     */
    public boolean push(String commandName, HttpServletRequest request) {
        IControllerCommand command = commandMap.get(commandName);
        if (command == null) {
            return false;
        }
        commandQueue.offer(new CommandExecution(command, request));
        return true;
    }
    
    /**
     * Pushes a command instance directly to the queue
     * @param command Command instance
     * @param request HTTP request
     */
    public void push(IControllerCommand command, HttpServletRequest request) {
        commandQueue.offer(new CommandExecution(command, request));
    }
    
    /**
     * Executes the next command in the queue
     * @return Response entity from the command execution, or error if queue is empty
     */
    public ResponseEntity<?> executeNext() {
        CommandExecution execution = commandQueue.poll();
        if (execution == null) {
            return ResponseEntity.badRequest().body("Command queue is empty");
        }
        return execution.command.execute(execution.request);
    }
    
    /**
     * Checks if the queue has pending commands
     * @return true if queue has commands
     */
    public boolean hasNext() {
        return !commandQueue.isEmpty();
    }
    
    /**
     * Gets the number of pending commands in the queue
     * @return Queue size
     */
    public int getQueueSize() {
        return commandQueue.size();
    }
    
    /**
     * Clears all pending commands from the queue
     */
    public void clearQueue() {
        commandQueue.clear();
    }
    
    /**
     * Invokes a command by name (legacy method - directly executes without queue)
     * @param commandName Command name
     * @param request HTTP request
     * @return Response entity
     * @deprecated Use push() and executeNext() for queue-based execution
     */
    @Deprecated
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
