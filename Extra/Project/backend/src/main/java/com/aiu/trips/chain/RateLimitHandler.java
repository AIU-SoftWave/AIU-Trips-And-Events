package com.aiu.trips.chain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RateLimitHandler as per Controller.pu diagram
 * Chain of Responsibility Pattern - Rate limiting for requests
 */
@Component
public class RateLimitHandler extends RequestHandler {

    @Value("${rate.limit.enabled:true}")
    private boolean rateLimitEnabled;

    @Value("${rate.limit.maxRequestsPerMinute:60}")
    private int maxRequestsPerMinute;

    @Value("${rate.limit.windowMs:60000}")
    private long windowSizeMs; // default 1 minute

    private final Map<String, RateLimitInfo> clientRequests = new ConcurrentHashMap<>();

    @Override
    public void handle(HttpServletRequest request) throws Exception {
        if (!rateLimitEnabled) {
            handleNext(request);
            return;
        }

        String clientId = getClientId(request);
        RateLimitInfo info = clientRequests.computeIfAbsent(clientId, k -> new RateLimitInfo());

        long currentTime = System.currentTimeMillis();

        // Reset if window has passed
        if (currentTime - info.windowStart > windowSizeMs) {
            info.reset(currentTime);
        }

        // Check rate limit
        if (info.requestCount >= maxRequestsPerMinute) {
            throw new RuntimeException("Rate limit exceeded. Please try again later.");
        }

        info.requestCount++;
        handleNext(request);
    }

    private String getClientId(HttpServletRequest request) {
        // Use IP address as client identifier
        String clientIp = request.getRemoteAddr();
        // In production, you might want to use X-Forwarded-For header for proxied
        // requests
        String forwardedFor = request.getHeader("X-Forwarded-For");
        return forwardedFor != null ? forwardedFor : clientIp;
    }

    private static class RateLimitInfo {
        long windowStart;
        int requestCount;

        RateLimitInfo() {
            reset(System.currentTimeMillis());
        }

        void reset(long time) {
            this.windowStart = time;
            this.requestCount = 0;
        }
    }
}
