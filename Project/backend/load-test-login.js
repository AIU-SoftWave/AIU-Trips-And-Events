import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate, Trend } from 'k6/metrics';

/**
 * k6 Load Testing Script for Login Component
 * 
 * Assignment Requirements:
 * - Target: P95 < 200ms @ 100 RPS
 * - Ramp-up: 30 seconds to 100 RPS
 * - Sustained Load: 10 minutes @ 100 RPS
 * - Realistic Data: Dynamic username/password rotation
 * 
 * Execution: k6 run --out json=results.json load-test-login.js
 */

// Custom metrics
const loginSuccessRate = new Rate('login_success_rate');
const loginDuration = new Trend('login_duration');

// Configuration
const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';
const TEST_USERS_COUNT = 100; // Number of test users to create

// Workload Profile as per assignment
export const options = {
    stages: [
        // Ramp-up: Gradually increase to 100 RPS over 30 seconds
        { duration: '30s', target: 100 },
        
        // Sustained Load: Maintain 100 RPS for 10 minutes
        { duration: '10m', target: 100 },
        
        // Ramp-down: Graceful shutdown
        { duration: '10s', target: 0 },
    ],
    
    // SLO Threshold: P95 must be less than 200ms
    thresholds: {
        'http_req_duration': ['p(95)<200'],  // Primary SLO
        'http_req_duration{name:login}': ['p(95)<200'],  // Specific to login
        'login_success_rate': ['rate>0.99'],  // 99% success rate
        'http_req_failed': ['rate<0.01'],  // Less than 1% errors
    },
    
    // Additional settings
    noConnectionReuse: false,  // Reuse connections for realistic scenario
    userAgent: 'K6LoadTest/1.0',
};

// Test data: Generate realistic user credentials
function generateTestUsers() {
    const users = [];
    for (let i = 1; i <= TEST_USERS_COUNT; i++) {
        users.push({
            email: `testuser${i}@aiu.edu`,
            password: `TestPass${i}!`,
            fullName: `Test User ${i}`,
            phoneNumber: `555-010${String(i).padStart(4, '0')}`
        });
    }
    return users;
}

const TEST_USERS = generateTestUsers();

/**
 * Setup Phase: Create test users
 * This runs once before the load test starts
 */
export function setup() {
    console.log('Setting up test users...');
    const setupStart = Date.now();
    let created = 0;
    
    for (const user of TEST_USERS) {
        const payload = JSON.stringify(user);
        const params = {
            headers: { 'Content-Type': 'application/json' },
            tags: { name: 'setup' }
        };
        
        const res = http.post(`${BASE_URL}/api/auth/optimized-register`, payload, params);
        if (res.status === 200 || res.status === 400) {
            // 200 = created, 400 = already exists (ok for repeated runs)
            created++;
        }
        
        // Rate limit setup to avoid overwhelming the system
        if (created % 10 === 0) {
            sleep(0.1);
        }
    }
    
    const setupDuration = (Date.now() - setupStart) / 1000;
    console.log(`Setup complete: ${created}/${TEST_USERS.length} users ready in ${setupDuration}s`);
    
    return { usersCount: TEST_USERS.length };
}

/**
 * Main Load Test: Realistic Login Scenario
 * 
 * Each VU (Virtual User) performs:
 * 1. Select a random user (simulates realistic traffic distribution)
 * 2. Send login request
 * 3. Validate response
 * 4. Brief think time
 */
export default function(data) {
    // Pattern: Random user selection (bypasses simple caching)
    // This simulates real traffic where different users log in
    const randomUser = TEST_USERS[Math.floor(Math.random() * TEST_USERS.length)];
    
    const payload = JSON.stringify({
        email: randomUser.email,
        password: randomUser.password
    });
    
    const params = {
        headers: { 'Content-Type': 'application/json' },
        tags: { name: 'login' }
    };
    
    // Execute login request
    const response = http.post(`${BASE_URL}/api/auth/optimized-login`, payload, params);
    
    // Validate response
    const success = check(response, {
        'status is 200': (r) => r.status === 200,
        'has token': (r) => {
            try {
                const body = JSON.parse(r.body);
                return body.token && body.token.length > 0;
            } catch {
                return false;
            }
        },
        'response time < 200ms': (r) => r.timings.duration < 200,
        'response time < 500ms': (r) => r.timings.duration < 500,
    });
    
    // Record metrics
    loginSuccessRate.add(success);
    loginDuration.add(response.timings.duration);
    
    // Realistic think time: Small pause between requests
    // This prevents coordinated omission and simulates real user behavior
    sleep(0.05);  // 50ms think time
}

/**
 * Teardown Phase: Summary Report
 */
export function teardown(data) {
    console.log('\n=== Load Test Complete ===');
    console.log(`Test Users: ${data.usersCount}`);
    console.log('Check results for P95 latency in summary statistics');
}

/**
 * COORDINATED OMISSION PREVENTION:
 * 
 * To prevent coordinated omission (where the load generator becomes the bottleneck):
 * 
 * 1. Monitor Load Generator CPU:
 *    - Run: top or htop during test
 *    - If CPU > 80%, the test results are invalid
 *    - Solution: Run k6 on a more powerful machine or use distributed mode
 * 
 * 2. Check k6 Dropped Iterations:
 *    - k6 will report "dropped_iterations" metric
 *    - If > 0, load generator is overloaded
 * 
 * 3. Validate Request Rate:
 *    - Expected: ~100 req/s sustained
 *    - Actual: Check "http_reqs" in summary
 *    - If actual << expected, load generator is bottlenecked
 * 
 * 4. Solution for Overload:
 *    - Use k6 cloud: k6 cloud run load-test-login.js
 *    - Or distributed k6: Run on multiple machines
 *    - Reduce VU count and increase iteration rate per VU
 */
