package com.aiu.trips.chain;

import com.aiu.trips.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AuthorizationHandler as per Controller.pu diagram
 * Chain of Responsibility Pattern - Validates user authorization/permissions
 */
@Component
public class AuthorizationHandler extends RequestHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void handle(HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // Skip for public endpoints (including public event browsing - GET requests)
        if (isPublicEndpoint(requestURI, method)) {
            handleNext(request);
            return;
        }

        // Only check authorization for non-public endpoints
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String role = jwtUtil.extractRole(token);

            // Check admin-only endpoints (admin panel and non-GET event operations)
            if (requestURI.contains("/api/admin/")) {
                if (!"ADMIN".equals(role) && !"ORGANIZER".equals(role)) {
                    throw new SecurityException("Insufficient permissions for this operation");
                }
            }
            
            // Only POST/PUT/DELETE on events require admin/organizer role
            if (requestURI.contains("/api/events") && !"GET".equals(method)) {
                if (!"ADMIN".equals(role) && !"ORGANIZER".equals(role)) {
                    throw new SecurityException("Insufficient permissions for this operation");
                }
            }
        }

        handleNext(request);
    }

    private boolean isPublicEndpoint(String uri, String method) {
        // Allow unauthenticated GET access to public event listings
        boolean isPublicEventGet = "GET".equalsIgnoreCase(method) && uri.startsWith("/api/events");

        return isPublicEventGet ||
                uri.contains("/api/auth/") ||
                uri.contains("/h2-console") ||
                uri.contains("/swagger");
    }
}
