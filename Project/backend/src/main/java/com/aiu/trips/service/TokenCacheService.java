package com.aiu.trips.service;

import com.aiu.trips.dto.AuthResponse;
import com.aiu.trips.dto.LoginRequest;
import com.aiu.trips.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Token Cache Service (Low-Latency Pattern #2: Redis Token Caching)
 * 
 * Pattern: Cache-Aside Pattern
 * Purpose: Store generated tokens and user authentication state in Redis
 * Impact: Reduces repeated JWT generation and database lookups
 * Expected Latency Reduction: 30-50ms on cache hits
 */
@Service
public class TokenCacheService {

    private static final String TOKEN_PREFIX = "token:";
    private static final String USER_AUTH_PREFIX = "user_auth:";
    private static final long TOKEN_CACHE_TTL = 3600; // 1 hour in seconds

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Cache a generated token for a user
     */
    public void cacheToken(String email, AuthResponse authResponse) {
        String key = TOKEN_PREFIX + email;
        redisTemplate.opsForValue().set(key, authResponse, TOKEN_CACHE_TTL, TimeUnit.SECONDS);
    }

    /**
     * Get cached token for a user
     */
    public AuthResponse getCachedToken(String email) {
        String key = TOKEN_PREFIX + email;
        return (AuthResponse) redisTemplate.opsForValue().get(key);
    }

    /**
     * Cache user authentication data to avoid database lookups
     */
    public void cacheUserAuth(String email, User user) {
        String key = USER_AUTH_PREFIX + email;
        redisTemplate.opsForValue().set(key, user, TOKEN_CACHE_TTL, TimeUnit.SECONDS);
    }

    /**
     * Get cached user authentication data
     */
    public User getCachedUserAuth(String email) {
        String key = USER_AUTH_PREFIX + email;
        return (User) redisTemplate.opsForValue().get(key);
    }

    /**
     * Invalidate cached tokens and user data (for logout/password change)
     */
    public void invalidateCache(String email) {
        redisTemplate.delete(TOKEN_PREFIX + email);
        redisTemplate.delete(USER_AUTH_PREFIX + email);
    }
}
