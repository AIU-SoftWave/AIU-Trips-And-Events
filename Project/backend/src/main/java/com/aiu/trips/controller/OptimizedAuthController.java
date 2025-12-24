package com.aiu.trips.controller;

import com.aiu.trips.dto.AuthResponse;
import com.aiu.trips.dto.LoginRequest;
import com.aiu.trips.dto.RegisterRequest;
import com.aiu.trips.service.OptimizedAuthService;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Optimized Authentication Controller for Low-Latency Login
 * 
 * Target: P95 < 200ms @ 100 RPS
 * 
 * This controller uses the OptimizedAuthService which implements:
 * - Pattern #1: HikariCP Connection Pooling
 * - Pattern #2: Redis Token Caching
 * - Pattern #3: Optimized Authentication Flow
 */
@RestController
@RequestMapping("/api/auth")
public class OptimizedAuthController {

    @Autowired
    private OptimizedAuthService optimizedAuthService;

    /**
     * Optimized Login Endpoint
     * 
     * Performance Characteristics:
     * - Cache Hit: ~10-30ms (Redis lookup + token validation)
     * - Cache Miss: ~100-150ms (DB query + password verification + token generation)
     * - Expected P95: <200ms under 100 RPS sustained load
     */
    @PostMapping("/optimized-login")
    @Timed(value = "http.login.time", description = "HTTP login endpoint time", percentiles = {0.5, 0.95, 0.99})
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = optimizedAuthService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Optimized Register Endpoint
     */
    @PostMapping("/optimized-register")
    @Timed(value = "http.register.time", description = "HTTP register endpoint time")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = optimizedAuthService.register(request);
        return ResponseEntity.ok(response);
    }
}
