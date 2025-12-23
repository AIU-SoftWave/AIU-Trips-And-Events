import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate, Trend, Counter } from 'k6/metrics';

// Custom metrics
const errorRate = new Rate('errors');
const responseTime = new Trend('response_time');
const successfulRequests = new Counter('successful_requests');

// Test configuration
export const options = {
  stages: [
    { duration: '30s', target: 50 },   // Ramp-up to 50 RPS
    { duration: '30s', target: 100 },  // Ramp-up to 100 RPS
    { duration: '5m', target: 100 },   // Sustained load at 100 RPS
    { duration: '30s', target: 0 },    // Ramp-down
  ],
  thresholds: {
    'http_req_duration': ['p(95)<200'], // 95% of requests must be below 200ms
    'http_req_duration{endpoint:auth}': ['p(95)<200'],
    'errors': ['rate<0.05'],            // Error rate must be below 5%
    'http_req_failed': ['rate<0.05'],
  },
  summaryTrendStats: ['avg', 'min', 'med', 'max', 'p(90)', 'p(95)', 'p(99)'],
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

export default function () {
  const loginPayload = JSON.stringify({
    email: 'admin@aiu.edu',
    password: 'admin123',
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
    tags: { endpoint: 'auth' },
  };

  // Test: Login endpoint
  const loginRes = http.post(`${BASE_URL}/api/auth/login`, loginPayload, params);
  
  check(loginRes, {
    'status is 200': (r) => r.status === 200,
    'response time < 200ms': (r) => r.timings.duration < 200,
    'response time < 500ms': (r) => r.timings.duration < 500,
    'has token': (r) => {
      if (r.status === 200) {
        const body = JSON.parse(r.body);
        return body.token !== undefined;
      }
      return false;
    },
  });

  errorRate.add(loginRes.status !== 200);
  responseTime.add(loginRes.timings.duration);
  
  if (loginRes.status === 200) {
    successfulRequests.add(1);
  }

  sleep(1); // Sleep to control the request rate
}

export function teardown(data) {
  console.log('Test completed');
}
