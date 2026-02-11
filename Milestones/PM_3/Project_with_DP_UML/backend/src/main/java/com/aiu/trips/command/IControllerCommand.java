package com.aiu.trips.command;

/**
 * Command Pattern - Base interface for all controller commands
 * Encapsulates requests as objects
 */
public interface IControllerCommand {
    Object execute();
}
