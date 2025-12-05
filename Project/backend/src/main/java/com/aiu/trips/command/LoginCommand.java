package com.aiu.trips.command;

import com.aiu.trips.service.interfaces.IAuthenticationUserManagement;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public class LoginCommand implements IControllerCommand {
    private final IAuthenticationUserManagement authService;

    public LoginCommand(IAuthenticationUserManagement authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<?> execute(Map<String, Object> requestData) {
        try {
            String email = (String) requestData.get("email");
            String password = (String) requestData.get("password");
            String token = authService.login(email, password);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
