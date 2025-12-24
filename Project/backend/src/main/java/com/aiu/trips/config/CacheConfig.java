package com.aiu.trips.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Cache Configuration for Low-Latency Performance
 * Implements Caching Pattern to achieve P95 < 200ms
 * 
 * Design Rationale:
 * - In-memory caching with Caffeine for sub-millisecond access
 * - TTL of 5 minutes to balance freshness and performance
 * - Maximum 1000 entries per cache to prevent memory issues
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
            "events",
            "upcomingEvents",
            "eventById",
            "bookings"
        );
        
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(1000)
            .recordStats(); // Enable cache statistics for monitoring
    }
}
