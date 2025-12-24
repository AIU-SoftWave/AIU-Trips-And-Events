import http from "k6/http";
import { check, sleep } from "k6";
import { Rate, Trend, Counter } from "k6/metrics";

// Custom metrics
const errorRate = new Rate("errors");
const responseTime = new Trend("response_time");
const successfulRequests = new Counter("successful_requests");

// Test configuration
export const options = {
  stages: [
    { duration: "30s", target: 50 }, // Ramp-up to 50 RPS
    { duration: "30s", target: 100 }, // Ramp-up to 100 RPS
    { duration: "5m", target: 100 }, // Sustained load at 100 RPS
    { duration: "30s", target: 0 }, // Ramp-down
  ],
  thresholds: {
    http_req_duration: ["p(95)<200"], // 95% of requests must be below 200ms
    "http_req_duration{endpoint:events}": ["p(95)<200"],
    errors: ["rate<0.05"], // Error rate must be below 5%
    http_req_failed: ["rate<0.05"],
  },
  summaryTrendStats: ["avg", "min", "med", "max", "p(90)", "p(95)", "p(99)"],
};

const BASE_URL = __ENV.BASE_URL || "http://localhost:8080";

// Test data
let authToken = "";

export function setup() {
  // Login to get JWT token
  const loginPayload = JSON.stringify({
    email: "admin@aiu.edu",
    password: "admin123",
  });

  const loginRes = http.post(`${BASE_URL}/api/auth/login`, loginPayload, {
    headers: { "Content-Type": "application/json" },
  });

  if (loginRes.status === 200) {
    const body = JSON.parse(loginRes.body);
    console.log(
      `Setup: Successfully logged in, token length: ${body.token.length}`
    );
    return { token: body.token };
  }

  console.error(
    `Setup failed: status=${loginRes.status}, body=${loginRes.body}`
  );
  throw new Error("Failed to authenticate - cannot proceed with test");
}

export default function (data) {
  // Validate token exists
  if (!data || !data.token) {
    console.error("No auth token available");
    errorRate.add(1);
    sleep(1);
    return;
  }

  const params = {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${data.token}`,
    },
    tags: { endpoint: "events" },
  };

  // Test: Get all events
  const eventsRes = http.get(`${BASE_URL}/api/events`, params);

  const checks = check(eventsRes, {
    "status is 200": (r) => r.status === 200,
    "response time < 200ms": (r) => r.timings.duration < 200,
    "response time < 500ms": (r) => r.timings.duration < 500,
  });

  errorRate.add(eventsRes.status !== 200);
  responseTime.add(eventsRes.timings.duration);

  if (eventsRes.status === 200) {
    successfulRequests.add(1);
  } else {
    // Log first few failures for debugging
    if (successfulRequests.value < 10) {
      console.error(
        `Request failed: status=${
          eventsRes.status
        }, body=${eventsRes.body.substring(0, 200)}`
      );
    }
  }

  sleep(1); // Sleep to control the request rate
}

export function teardown(data) {
  console.log("Test completed");
}
