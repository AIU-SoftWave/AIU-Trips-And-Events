package com.aiu.trips.service.interfaces;

import com.aiu.trips.dto.AuthResponse;
import com.aiu.trips.dto.LoginRequest;

public interface IAuthenticationService {
    AuthResponse authenticate(LoginRequest request);
    boolean validateToken(String token);
    boolean logout(String token);
    boolean resetPassword(String email);
}
