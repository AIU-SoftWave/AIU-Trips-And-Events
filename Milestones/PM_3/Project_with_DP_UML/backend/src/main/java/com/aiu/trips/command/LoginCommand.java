package com.aiu.trips.command;

import com.aiu.trips.dto.LoginRequest;
import com.aiu.trips.service.AuthService;

/**
 * Command Pattern - Concrete command for user login
 */
public class LoginCommand implements IControllerCommand {
    
    private final AuthService authService;
    private final LoginRequest loginRequest;
    
    public LoginCommand(AuthService authService, LoginRequest loginRequest) {
        this.authService = authService;
        this.loginRequest = loginRequest;
    }
    
    @Override
    public Object execute() {
        return authService.login(loginRequest);
    }
}
