# Low-Latency Component Implementation Assignment

## Objective

Implement one component of your project and ensure its response time is consistently less than **200 milliseconds**. To achieve this, you must adopt appropriate low-latency design patterns.

## Requirements

1. **Choose and implement suitable low-latency design patterns** for your component
2. **Document how you achieved the 200ms constraint**
3. **Perform response time testing** and show the percentage of requests satisfying the constraint (assuming 100 requests/sec workload)
4. **Provide a PowerPoint presentation and demo** of your work

## Performance Testing Tools

You can use any of the following tools to assess your component's performance:
- **Apache JMeter**
- **Gatling**
- **k6**

## References

- Research Paper: https://arxiv.org/pdf/2309.04259
- Video Tutorial: https://www.youtube.com/watch?v=q7qKeUVS4Gw&t=9s

## Important Notes

- If multiple students from the same project are registered for extracurricular, each must demonstrate a **different component**
- These topics may be new, but do your best
- **Professional work and comprehensive reports will be rewarded with bonus points**

---

## Testing Environment Setup Guide

Setting up an environment to test for **P95 < 200 msec** response time with a sustained workload of **100 Requests Per Second (RPS)** requires careful planning in three main areas:

1. The Test Environment
2. The Load Generation Tool
3. The Test Configuration

### 1. Prepare the Test Environment

The test environment must accurately simulate your production environment to get meaningful results.

#### A. Component Under Test (CUT)

**Isolation:**
- The component must be isolated from other development or QA traffic
- This prevents external factors (e.g., concurrent database queries) from skewing latency measurements

**Parity:**
Ensure the CUT's deployment configuration matches production as closely as possible:
- **Hardware/VM/Container specs:** CPU, RAM, and network settings must be identical
- **Operating System/Runtime:** Same OS, Java/Python/Node.js version, etc.
- **Dependencies:** Use a production-like database with:
  - Same version and configuration
  - Production-sized dataset with similar indexing and data distribution
  - Note: The database is often the source of "tail latency" (P95/P99 issues)

#### B. Monitoring Infrastructure

**Observability:**
Set up robust monitoring (e.g., Prometheus/Grafana, Datadog, or New Relic) on the CUT server(s). You must correlate response time spikes with system metrics.

**Key Metrics to Monitor:**
- **Server CPU Utilization:** Should not be pegged at 100% (high CPU often causes latency)
- **Server Memory Usage:** Watch for memory leaks or excessive Garbage Collection (GC) activity (major causes of P95 spikes)
- **Network I/O/Latency:** Between the CUT and its dependencies (e.g., database, caching layer)
- **Database Metrics:** Query response times, connection pool saturation, and I/O

### 2. Choose and Configure the Load Tool

Select a modern load testing tool that can accurately measure percentiles and sustain the required load from the client side.

#### A. Tool Selection

Popular, reliable tools that support percentile (P95) tracking and high throughput:

| Tool | Description |
|------|-------------|
| **k6** | Modern, code-based, great for CI/CD integration |
| **Gatling** | Scala-based, known for high performance |
| **Locust** | Python-based, good for complex user flows |
| **Apache JMeter** | Highly flexible, but requires careful configuration for high load/percentile accuracy |

#### B. Load Generator Setup

The machine running the load test (the load generator) must be powerful enough not to become the bottleneck.

**Client Resource Check:**
- For 100 RPS, a single modern machine is typically sufficient
- Monitor its CPU and network usage
- If the load generator hits 80%+ CPU, its own processing delay will skew your measured latency (an issue called **Coordinated Omission**)

**Distributed Load:**
- If you cannot rule out the load generator as a bottleneck, use multiple distributed load generator agents in the same region as the CUT

### 3. Design and Execute the Test

Your test script must enforce the target workload and define the critical performance threshold.

#### A. Load Script Design

**Realistic Flow:**
- Script the request(s) that represent the most frequent or performance-critical operation
- Use dynamic data (e.g., rotating user IDs, unique payload values) to prevent the component from serving only cached responses

**Throughput Control:**
- Configure the tool to maintain a constant target rate of 100 RPS
- Tools like k6, Gatling, or JMeter's Throughput Shaping Timer allow you to define this rate explicitly, ensuring stable load

#### B. Defining the Objective (The SLO)

The **P95** is the Service Level Objective (SLO) you are testing for:
- This means **95% of all requests** in the test run must have a response time of **less than 200 msec**

#### C. Setting the Threshold in the Tool

Configure a pass/fail threshold directly in your load test tool.

**Example (k6):**
```javascript
thresholds: {
  'http_req_duration': ['p(95) < 200'] // 95th percentile response time must be under 200ms
}
```

**Ramp-Up/Duration:**
- **Ramp-Up:** Start with a gradual ramp-up (e.g., over 30 seconds) to 100 RPS to warm up the component and caches
- **Sustained Load:** Run the test for a minimum of 5-10 minutes at the full 100 RPS to gather sufficient data and detect any degradation

#### D. Analysis

The tool's final report will provide the exact P95 value:

- **If P95 < 200 msec:** ✅ The test passes for this workload
- **If P95 > 200 msec:** ❌ The test fails
  - Correlate the time of the P95 spikes with your system monitoring data (CPU, GC, DB I/O) to identify the bottleneck
