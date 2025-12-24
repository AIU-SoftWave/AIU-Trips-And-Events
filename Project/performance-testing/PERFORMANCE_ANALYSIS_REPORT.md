# Performance Analysis Report
## AIU Trips & Events - Performance-Critical Component Testing

**Course**: CSE352 System Analysis and Design  
**Assignment**: Extracurricular Activity - Performance Testing  
**Date**: December 24, 2025  
**Component**: Events API (`/api/v2/events`)  
**Success Criteria**: P95 Latency < 200ms @ 100 RPS  

---

## Executive Summary

### Test Overview
This report presents the performance analysis of the Events API component under sustained load testing. The component was subjected to 100 requests per second (RPS) for 10 minutes to validate the P95 latency requirement of less than 200 milliseconds.

### Key Results
| Metric | Target | Actual | Status |
|--------|--------|--------|--------|
| P95 Latency | < 200ms | [TBD]ms | [✓/✗] |
| P99 Latency | < 500ms | [TBD]ms | [✓/✗] |
| Error Rate | < 1% | [TBD]% | [✓/✗] |
| Throughput | 100 RPS | [TBD] RPS | [✓/✗] |
| Cache Hit Rate | > 80% | [TBD]% | [✓/✗] |

### Conclusion
[Summary: The system met/did not meet the performance requirements. Key factors contributing to success/failure were...]

---

## 1. Test Configuration

### 1.1 System Under Test

**Component**: Performance-Optimized Events API  
**Version**: 1.0.0  
**Technology Stack**:
- Backend: Spring Boot 3.2.0 + Java 17
- Database: PostgreSQL 15
- Cache: Caffeine (in-memory)
- Monitoring: Prometheus + Grafana

**API Endpoints Tested**:
1. `GET /api/v2/events` - Retrieve all active events (40% of load)
2. `GET /api/v2/events/upcoming` - Retrieve upcoming events (30% of load)
3. `GET /api/v2/events/{id}` - Retrieve event by ID (30% of load)

### 1.2 Test Environment

**Hardware Configuration**:
```
Server:
- CPU: [TBD] cores @ [TBD] GHz
- Memory: [TBD] GB RAM
- Storage: [TBD] SSD
- Network: [TBD] Gbps

Load Generator:
- CPU: [TBD] cores @ [TBD] GHz
- Memory: [TBD] GB RAM
```

**Software Configuration**:
```
Operating System: Ubuntu 22.04 LTS
Java Version: OpenJDK 17.0.x
Database: PostgreSQL 15.x
Container Runtime: Docker 24.x
```

**Network Topology**:
```
[k6 Load Generator] ---> [Spring Boot App] ---> [PostgreSQL DB]
                              |
                              v
                    [Prometheus/Grafana]
```

### 1.3 Dataset

**Database Contents**:
- Events: 10,000 records
- Users: 1,000 records
- Bookings: 5,000 records
- Distribution: 70% upcoming, 20% ongoing, 10% completed events

**Indexes Created**:
- `idx_events_status` on events(status)
- `idx_events_start_date` on events(start_date)
- `idx_events_status_start_date` on events(status, start_date)

### 1.4 Load Test Scenario

**Test Tool**: k6 Load Testing Framework  
**Test Duration**: 11 minutes total
- Ramp-up: 30 seconds (0 → 100 RPS)
- Sustained: 10 minutes (100 RPS)
- Ramp-down: 30 seconds (100 → 0 RPS)

**Virtual Users**: Dynamic (50-200 VUs)  
**Total Requests**: ~60,000  
**Request Distribution**: 40% list / 30% upcoming / 30% by-id

---

## 2. Results Overview

### 2.1 Latency Metrics

**HTTP Request Duration** (in milliseconds):

| Percentile | Value (ms) | Target (ms) | Status |
|------------|------------|-------------|--------|
| P50 (Median) | [TBD] | N/A | - |
| P75 | [TBD] | N/A | - |
| P90 | [TBD] | N/A | - |
| P95 | [TBD] | < 200 | [✓/✗] |
| P99 | [TBD] | < 500 | [✓/✗] |
| Max | [TBD] | N/A | - |

**Latency Distribution Chart**:
```
[Insert chart: Latency over time - Line graph showing P50/P95/P99]
```

**Key Observations**:
- [TBD: Describe the latency pattern - stable, spiky, trending up/down]
- [TBD: Identify any anomalies or interesting patterns]
- [TBD: Compare cache hit vs cache miss latencies]

### 2.2 Throughput & Error Rate

**Request Statistics**:
- Total Requests: [TBD]
- Successful (2xx): [TBD] ([TBD]%)
- Client Errors (4xx): [TBD] ([TBD]%)
- Server Errors (5xx): [TBD] ([TBD]%)
- Failed Requests: [TBD] ([TBD]%)

**Throughput Over Time**:
```
[Insert chart: Requests per second over time]
```

**Error Rate Analysis**:
```
[Insert chart: Error rate percentage over time]
```

### 2.3 Cache Performance

**Cache Hit Statistics**:
- Total Cache Requests: [TBD]
- Cache Hits: [TBD] ([TBD]%)
- Cache Misses: [TBD] ([TBD]%)
- Cache Evictions: [TBD]

**Impact on Latency**:
- Avg Latency (Cache Hit): [TBD]ms
- Avg Latency (Cache Miss): [TBD]ms
- Performance Gain: [TBD]x faster

```
[Insert chart: Cache hit rate over time]
```

---

## 3. System Metrics Correlation

### 3.1 CPU Usage

**CPU Metrics**:
- Average System CPU: [TBD]%
- Peak System CPU: [TBD]%
- Average Process CPU: [TBD]%
- Peak Process CPU: [TBD]%

**CPU vs Latency Correlation**:
```
[Insert chart: CPU usage overlaid with P95 latency]
```

**Analysis**:
- [TBD: Describe correlation - e.g., "Latency spikes observed when CPU exceeds 75%"]
- [TBD: Identify CPU bottlenecks]
- [TBD: CPU efficiency assessment]

### 3.2 Memory Usage

**JVM Heap Metrics**:
- Initial Heap: 256 MB
- Max Heap: 512 MB
- Average Used: [TBD] MB ([TBD]%)
- Peak Used: [TBD] MB ([TBD]%)

**Memory Pressure**:
```
[Insert chart: Heap memory usage over time]
```

**Analysis**:
- [TBD: Memory stability - was heap usage stable?]
- [TBD: Any memory leaks detected?]
- [TBD: Headroom available for higher load]

### 3.3 Garbage Collection

**GC Statistics**:
- Total GC Pauses: [TBD]
- Average GC Pause: [TBD]ms
- Max GC Pause: [TBD]ms
- Time in GC: [TBD]% of test duration

**GC vs Latency Correlation**:
```
[Insert chart: GC pause events overlaid with latency spikes]
```

**Analysis**:
- [TBD: GC impact on latency - "50ms latency spikes correlate with minor GC"]
- [TBD: GC frequency and duration assessment]
- [TBD: Recommendations for GC tuning]

### 3.4 Database Connections

**Connection Pool Metrics**:
- Pool Size: 20 connections
- Average Active: [TBD]
- Peak Active: [TBD]
- Average Idle: [TBD]
- Connection Wait Time: [TBD]ms avg

**Pool Saturation Analysis**:
```
[Insert chart: Active vs idle connections over time]
```

**Analysis**:
- [TBD: Pool utilization - was the pool saturated?]
- [TBD: Connection contention detected?]
- [TBD: Pool sizing assessment]

### 3.5 Database I/O

**Query Performance**:
- Average Query Time: [TBD]ms
- Slowest Query: [TBD]ms
- Queries per Second: [TBD]
- Index Usage: [TBD]%

**Database Metrics**:
```
[Insert chart: Database query latency distribution]
```

**Analysis**:
- [TBD: Database bottlenecks identified]
- [TBD: Index effectiveness]
- [TBD: Query optimization opportunities]

---

## 4. Bottleneck Analysis

### 4.1 Identified Bottlenecks

**Bottleneck #1: [Title]**
- **Symptom**: [e.g., "P95 latency spikes to 250ms every 60 seconds"]
- **Metric Correlation**: [e.g., "Correlates with minor GC events (50ms pauses)"]
- **Root Cause**: [e.g., "Object allocation rate exceeds nursery space capacity"]
- **Impact**: [e.g., "Affects 5% of requests, adds 80ms to P95"]
- **Evidence**:
  ```
  [Insert relevant chart or log excerpt]
  ```

**Bottleneck #2: [Title]**
- **Symptom**: [TBD]
- **Metric Correlation**: [TBD]
- **Root Cause**: [TBD]
- **Impact**: [TBD]
- **Evidence**: [TBD]

**Bottleneck #3: [Title]**
- **Symptom**: [TBD]
- **Metric Correlation**: [TBD]
- **Root Cause**: [TBD]
- **Impact**: [TBD]
- **Evidence**: [TBD]

### 4.2 Correlation Matrix

| Latency Spike | CPU | Memory | GC | DB Pool | Network | Conclusion |
|---------------|-----|--------|----|---------|---------| ----------|
| Event #1 @ 2:15 | ↑ 85% | → 60% | ✓ 45ms | → 10 | → | GC-induced |
| Event #2 @ 5:30 | → 55% | → 58% | ✗ | ↑ 20 | → | Pool saturation |
| Event #3 @ 8:45 | ↑ 78% | → 62% | ✗ | → 12 | ↑ | CPU bottleneck |

**Legend**: ↑ High | → Normal | ✓ Detected | ✗ Not detected

### 4.3 Performance Ceiling

**Theoretical Maximum Throughput**:
```
Calculation:
- Avg response time: [TBD]ms
- Concurrent threads: 20 (connection pool)
- Max RPS = (1000ms / [TBD]ms) × 20 = [TBD] RPS
```

**Observed Maximum Throughput**: [TBD] RPS  
**Headroom**: [TBD]% ([TBD] RPS to [TBD] RPS)  
**Limiting Factor**: [CPU / Memory / Database / Network]

---

## 5. Low-Latency Design Validation

### 5.1 Caching Pattern Effectiveness

**Expected Benefit**: 95% latency reduction on cache hits  
**Actual Benefit**: [TBD]% latency reduction

**Validation**:
- Cache hit rate: [TBD]% (target: > 80%) → [✓/✗]
- Latency improvement: [TBD]x (target: > 5x) → [✓/✗]
- TTL effectiveness: [TBD] (no stale data served) → [✓/✗]

**Conclusion**: [The caching pattern successfully reduced latency / Caching effectiveness was limited by...]

### 5.2 Query Optimization Effectiveness

**Expected Benefit**: 90% query time reduction via indexes  
**Actual Benefit**: [TBD]% query time reduction

**Validation**:
- Index scan ratio: [TBD]% (target: > 95%) → [✓/✗]
- Query time: [TBD]ms avg (target: < 20ms) → [✓/✗]
- Full table scans: [TBD] (target: 0) → [✓/✗]

**Conclusion**: [Indexes effectively optimized queries / Further optimization needed...]

### 5.3 Connection Pooling Effectiveness

**Expected Benefit**: Eliminate connection acquisition latency  
**Actual Benefit**: [TBD]ms saved per request

**Validation**:
- Pool saturation: [TBD]% (target: < 80%) → [✓/✗]
- Avg acquisition time: [TBD]ms (target: < 5ms) → [✓/✗]
- Wait events: [TBD] (target: < 1% of requests) → [✓/✗]

**Conclusion**: [Connection pooling prevented bottlenecks / Pool size needs adjustment...]

### 5.4 Overall Design Assessment

**Design Pattern Scorecard**:
| Pattern | Implementation | Effectiveness | Grade |
|---------|---------------|---------------|-------|
| Caching | ✓ Caffeine, 5min TTL | [TBD]% improvement | [A/B/C/D] |
| Query Optimization | ✓ B-tree indexes | [TBD]% improvement | [A/B/C/D] |
| Connection Pool | ✓ HikariCP, 20 conns | [TBD]% improvement | [A/B/C/D] |
| Async Processing | ✓ @Transactional | [TBD]% improvement | [A/B/C/D] |

**Overall Grade**: [A/B/C/D/F]  
**Assessment**: [The design patterns collectively achieved/did not achieve the 200ms target...]

---

## 6. Optimization Recommendations

### 6.1 Immediate Actions (Quick Wins)

**Recommendation #1: [Title]**
- **Issue**: [TBD]
- **Solution**: [TBD]
- **Implementation Effort**: [Low/Medium/High]
- **Expected Impact**: [TBD]ms improvement
- **Priority**: [Critical/High/Medium/Low]

**Recommendation #2: [Title]**
- **Issue**: [TBD]
- **Solution**: [TBD]
- **Implementation Effort**: [Low/Medium/High]
- **Expected Impact**: [TBD]ms improvement
- **Priority**: [Critical/High/Medium/Low]

### 6.2 Medium-Term Actions

**Recommendation #3: [Title]**
- **Issue**: [TBD]
- **Solution**: [TBD]
- **Implementation Effort**: [Low/Medium/High]
- **Expected Impact**: [TBD]ms improvement
- **Priority**: [Critical/High/Medium/Low]

### 6.3 Long-Term Architectural Improvements

**Recommendation #4: [Title]**
- **Issue**: [TBD]
- **Solution**: [TBD]
- **Implementation Effort**: [Low/Medium/High]
- **Expected Impact**: [TBD]ms improvement
- **Priority**: [Critical/High/Medium/Low]

### 6.4 Capacity Planning

**Current Capacity**: [TBD] RPS @ P95 < 200ms  
**Required Capacity**: [TBD] RPS (growth projection)  
**Gap**: [TBD] RPS

**Scaling Strategy**:
1. Horizontal scaling: [TBD] instances
2. Vertical scaling: [TBD] CPU/memory upgrade
3. Database scaling: [TBD] read replicas
4. CDN/edge caching: [TBD]

**Cost Estimate**: [TBD]

---

## 7. Conclusion

### 7.1 Success Criteria Assessment

| Criterion | Target | Actual | Met? |
|-----------|--------|--------|------|
| P95 Latency | < 200ms | [TBD]ms | [✓/✗] |
| P99 Latency | < 500ms | [TBD]ms | [✓/✗] |
| Error Rate | < 1% | [TBD]% | [✓/✗] |
| Throughput | 100 RPS | [TBD] RPS | [✓/✗] |
| Cache Hit Rate | > 80% | [TBD]% | [✓/✗] |

**Overall Result**: [PASS / FAIL]

### 7.2 Key Findings

1. [TBD: Main finding #1]
2. [TBD: Main finding #2]
3. [TBD: Main finding #3]

### 7.3 Lessons Learned

**What Worked Well**:
- [TBD]
- [TBD]

**What Didn't Work**:
- [TBD]
- [TBD]

**Surprises**:
- [TBD]
- [TBD]

### 7.4 Next Steps

1. [TBD: Action item #1]
2. [TBD: Action item #2]
3. [TBD: Action item #3]

---

## Appendix A: Test Execution Log

```
[Insert complete k6 output]
```

## Appendix B: System Configuration

### Spring Boot Configuration
```properties
[Insert relevant application.properties]
```

### Database Configuration
```sql
[Insert relevant schema and indexes]
```

## Appendix C: Grafana Dashboard Screenshots

### Dashboard 1: Overview
```
[Insert screenshot]
```

### Dashboard 2: Detailed Metrics
```
[Insert screenshot]
```

## Appendix D: Prometheus Queries

```promql
# P95 Latency Query
histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket{uri=~"/api/v2/events.*"}[1m])) by (le)) * 1000

# More queries...
```

## Appendix E: Raw Data

[Attach: summary.json, prometheus-export.csv, etc.]

---

**Report Prepared By**: [Your Name]  
**Course**: CSE352 System Analysis and Design  
**Instructor**: [Instructor Name]  
**Date**: December 24, 2025  
**Version**: 1.0
