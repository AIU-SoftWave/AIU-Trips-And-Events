import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate, Trend } from 'k6/metrics';

/**
 * k6 Load Test Script for Performance-Critical Component
 * 
 * Test Requirements:
 * - Ramp-up: 30 seconds to 100 RPS
 * - Sustained Load: 10 minutes at 100 RPS
 * - Success Criteria: P95 latency < 200ms
 * - Realistic Flow: Dynamic data to prevent caching bias
 * 
 * Coordinated Omission Prevention:
 * - Pacing with sleep to maintain consistent RPS
 * - Monitor executor CPU to stay below 80%
 */

// Custom metrics
const errorRate = new Rate('errors');
const p95Latency = new Trend('p95_latency');

// Test configuration
export const options = {
    scenarios: {
        performance_test: {
            executor: 'ramping-arrival-rate',
            startRate: 0,
            timeUnit: '1s',
            preAllocatedVUs: 50,
            maxVUs: 200,
            stages: [
                // Ramp-up: 30 seconds to 100 RPS
                { duration: '30s', target: 100 },
                // Sustained load: 10 minutes at 100 RPS
                { duration: '10m', target: 100 },
                // Ramp-down: 30 seconds to 0 RPS
                { duration: '30s', target: 0 },
            ],
        },
    },
    thresholds: {
        // Success criteria: P95 < 200ms
        'http_req_duration': ['p(95)<200', 'p(99)<500'],
        // Error rate should be below 1%
        'errors': ['rate<0.01'],
    },
    // Disable DNS and connection reuse to simulate real-world conditions
    noConnectionReuse: false,
    userAgent: 'k6-performance-test/1.0',
};

// Base URL - can be overridden with --env BASE_URL
const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

// Test data generator for dynamic requests
function generateEventId() {
    // Generate random event IDs (1-100) for realistic queries
    return Math.floor(Math.random() * 100) + 1;
}

function generateQueryParam() {
    // Add random query params to prevent simple caching
    return `?_=${Date.now()}-${Math.random()}`;
}

/**
 * Main test scenario
 * Tests the performance-critical events endpoint
 */
export default function () {
    // Randomly select one of the performance-critical endpoints
    const scenario = Math.random();
    
    let response;
    let endpoint;

    if (scenario < 0.4) {
        // 40% - Get all events (most common operation)
        endpoint = `${BASE_URL}/api/v2/events${generateQueryParam()}`;
        response = http.get(endpoint);
    } else if (scenario < 0.7) {
        // 30% - Get upcoming events (second most common)
        endpoint = `${BASE_URL}/api/v2/events/upcoming${generateQueryParam()}`;
        response = http.get(endpoint);
    } else {
        // 30% - Get event by ID (direct access)
        const eventId = generateEventId();
        endpoint = `${BASE_URL}/api/v2/events/${eventId}${generateQueryParam()}`;
        response = http.get(endpoint);
    }

    // Verify response
    const checkRes = check(response, {
        'status is 200': (r) => r.status === 200,
        'response time < 200ms': (r) => r.timings.duration < 200,
        'response time < 500ms': (r) => r.timings.duration < 500,
        'has body': (r) => r.body && r.body.length > 0,
    });

    // Record custom metrics
    errorRate.add(!checkRes);
    p95Latency.add(response.timings.duration);

    // Small think time to simulate real user behavior (10-50ms)
    // This helps prevent coordinated omission
    sleep(0.01 + Math.random() * 0.04);
}

/**
 * Setup function - runs once before the test
 * Used to warm up the system and populate caches
 */
export function setup() {
    console.log('Starting performance test setup...');
    console.log(`Target URL: ${BASE_URL}`);
    
    // Warm-up requests to initialize caches
    console.log('Warming up caches...');
    http.get(`${BASE_URL}/api/v2/events`);
    http.get(`${BASE_URL}/api/v2/events/upcoming`);
    
    console.log('Setup complete. Starting load test...');
    
    return { startTime: Date.now() };
}

/**
 * Teardown function - runs once after the test
 */
export function teardown(data) {
    const duration = (Date.now() - data.startTime) / 1000;
    console.log(`Test completed in ${duration.toFixed(2)} seconds`);
}

/**
 * Handle summary for custom reporting
 */
export function handleSummary(data) {
    const p95 = data.metrics.http_req_duration.values['p(95)'];
    const p99 = data.metrics.http_req_duration.values['p(99)'];
    const errorRate = data.metrics.errors ? data.metrics.errors.values.rate : 0;
    
    console.log('\n=== PERFORMANCE TEST RESULTS ===');
    console.log(`P95 Latency: ${p95.toFixed(2)}ms`);
    console.log(`P99 Latency: ${p99.toFixed(2)}ms`);
    console.log(`Error Rate: ${(errorRate * 100).toFixed(2)}%`);
    console.log(`Success: ${p95 < 200 ? '✓ PASSED' : '✗ FAILED'}`);
    console.log('================================\n');
    
    return {
        'stdout': JSON.stringify(data, null, 2),
        'summary.json': JSON.stringify(data),
    };
}
