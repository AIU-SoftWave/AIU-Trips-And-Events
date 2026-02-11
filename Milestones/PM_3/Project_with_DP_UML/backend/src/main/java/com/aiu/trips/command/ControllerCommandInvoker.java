package com.aiu.trips.command;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Command Pattern - Invoker that executes commands
 * Manages the execution of controller commands
 */
@Component
public class ControllerCommandInvoker {
    
    private List<IControllerCommand> commandHistory = new ArrayList<>();
    
    public Object executeCommand(IControllerCommand command) {
        commandHistory.add(command);
        return command.execute();
    }
    
    public List<IControllerCommand> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }
    
    public void clearHistory() {
        commandHistory.clear();
    }
}
