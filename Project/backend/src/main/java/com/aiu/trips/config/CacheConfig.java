package com.aiu.trips.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${cache.ttl.minutes:5}")
    private int cacheTtlMinutes;

    @Value("${cache.max.size:1000}")
    private int cacheMaxSize;

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
            .expireAfterWrite(cacheTtlMinutes, TimeUnit.MINUTES)
            .maximumSize(cacheMaxSize)
            .recordStats(); // Enable cache statistics for monitoring
    }
}
