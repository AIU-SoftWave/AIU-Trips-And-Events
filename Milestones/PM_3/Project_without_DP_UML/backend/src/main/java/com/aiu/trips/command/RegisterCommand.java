package com.aiu.trips.command;

import com.aiu.trips.dto.RegisterRequest;
import com.aiu.trips.service.AuthService;

/**
 * Command Pattern - Concrete command for user registration
 */
public class RegisterCommand implements IControllerCommand {
    
    private final AuthService authService;
    private final RegisterRequest registerRequest;
    
    public RegisterCommand(AuthService authService, RegisterRequest registerRequest) {
        this.authService = authService;
        this.registerRequest = registerRequest;
    }
    
    @Override
    public Object execute() {
        return authService.register(registerRequest);
    }
}
