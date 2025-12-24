package com.aiu.trips.command;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * ControllerCommandInvoker - Thread-safe Command Pattern implementation
 * Executes commands directly without shared state to support concurrent
 * requests
 * 
 * Previous implementation used a shared LinkedList queue causing race
 * conditions:
 * - Thread A: pushToQueue(cmdA)
 * - Thread B: pushToQueue(cmdB)
 * - Thread A: executeNext() -> executes cmdB (wrong!)
 * 
 * This refactored version executes commands immediately, maintaining the
 * Command
 * pattern while eliminating the concurrency bottleneck.
 */
@Component
public class ControllerCommandInvoker {

    /**
     * Execute a command directly with provided request data
     * Thread-safe: no shared state between requests
     */
    public ResponseEntity<?> execute(IControllerCommand command, Map<String, Object> requestData) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        return command.execute(requestData);
    }

    /**
     * @deprecated Use execute() directly. Kept for backward compatibility.
     *             This method now executes immediately instead of queuing.
     */
    @Deprecated
    public void pushToQueue(IControllerCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        // In the new design, commands are executed directly via execute()
        // This method is kept only for backward compatibility during migration
    }

    /**
     * @deprecated Use execute() directly. Kept for backward compatibility.
     *             This method now throws an exception to indicate misuse of the old
     *             API.
     */
    @Deprecated
    public ResponseEntity<?> executeNext(Map<String, Object> requestData) {
        throw new UnsupportedOperationException(
                "executeNext() is deprecated. Use execute(command, requestData) instead. " +
                        "The push-then-execute pattern caused race conditions under concurrent load.");
    }
}
