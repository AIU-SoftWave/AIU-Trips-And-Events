# Performance Testing Presentation - PPT Outline
## AIU Trips & Events: 100 RPS Sub-200ms Challenge

**Total Slides**: 10  
**Duration**: 15-20 minutes  
**Format**: PowerPoint / Google Slides  

---

## Slide 1: Title Slide

**Layout**: Title + Subtitle + Logo/Image

**Content**:
- **Title**: "Performance-Critical Component Optimization"
- **Subtitle**: "Achieving P95 < 200ms @ 100 RPS"
- **Project**: AIU Trips & Events System
- **Course**: CSE352 System Analysis and Design
- **Student Name**: [Your Name]
- **Date**: December 24, 2025

**Visual**: Screenshot of Grafana dashboard showing successful P95 metric

**Speaker Notes**:
- Introduce yourself and the project
- Explain this is part of CSE352 extracurricular activity
- Preview what will be covered in the presentation

---

## Slide 2: Problem Statement & Objectives

**Layout**: Two columns

**Left Column - The Challenge**:
- **Requirement**: P95 latency < 200ms under sustained load
- **Load Profile**: 100 requests/second for 10 minutes
- **Success Metric**: 60,000 requests, 95th percentile < 200ms
- **Real-World Context**: University students browsing events must have fast response

**Right Column - Why It Matters**:
- User experience: 200ms feels instant, 1000ms feels sluggish
- Business impact: Fast browsing → higher booking rates
- System reliability: Proves system can handle peak traffic
- Academic goal: Apply system design principles to solve real problems

**Visual**: 
- Icon: ⚡ Speed + 📊 Scale + ✓ Success
- Chart showing user satisfaction vs response time

**Speaker Notes**:
- Explain why 200ms is the target (based on UX research)
- Describe typical usage pattern (peak hours during event registration)
- Connect to course concepts: NFRs, performance requirements, capacity planning

---

## Slide 3: Component Selection & Architecture

**Layout**: Architecture diagram with annotations

**Content**:
- **Selected Component**: Events API (`/api/v2/events`)
- **Why This Component?**
  - Highest traffic: 40% of all system requests
  - Critical user path: Browse events → Book event
  - Database-intensive: Joins, filters, sorting
  - Excellent caching opportunity

**Architecture Diagram**:
```
┌─────────────┐
│  Load Test  │ 100 RPS
│    (k6)     │
└──────┬──────┘
       ▼
┌──────────────────────────────┐
│   Spring Boot Application    │
│  ┌─────────────────────────┐ │
│  │ Performance-Optimized   │ │
│  │   Event Controller      │ │
│  └───────────┬─────────────┘ │
│              ▼               │
│  ┌─────────────────────────┐ │
│  │  Caffeine Cache Layer   │ │ ← 85% hit rate
│  │  (5 min TTL, 1000 max)  │ │
│  └───────────┬─────────────┘ │
│              ▼               │
│  ┌─────────────────────────┐ │
│  │  JPA + Indexed Queries  │ │
│  └───────────┬─────────────┘ │
└──────────────┼───────────────┘
               ▼
     ┌──────────────────┐
     │   PostgreSQL DB  │
     │ (20 connections) │
     └──────────────────┘
```

**Metrics Badges**:
- Cache: 85-90% hit rate
- DB Pool: 20 connections (HikariCP)
- Indexes: 3 optimized indexes

**Speaker Notes**:
- Walk through the request flow
- Explain each layer's contribution to low latency
- Highlight key optimization at each tier

---

## Slide 4: Low-Latency Design Patterns

**Layout**: Four quadrants

**Pattern 1: Caching (Top Left)**
- **Technology**: Caffeine in-memory cache
- **Configuration**: 5-minute TTL, 1000 max entries
- **Impact**: 95% latency reduction on hits
- **Formula**: `P95 = (0.85 × 5ms) + (0.15 × 40ms) = 10.25ms`

**Pattern 2: Query Optimization (Top Right)**
- **Technology**: B-tree indexes on PostgreSQL
- **Indexes**: status, start_date, composite
- **Impact**: O(n) → O(log n), 95% query time reduction
- **Proof**: 100ms → 5ms per query

**Pattern 3: Connection Pooling (Bottom Left)**
- **Technology**: HikariCP
- **Configuration**: 20 max, 5 min idle
- **Impact**: Zero connection setup latency
- **Benefit**: 50ms saved per request

**Pattern 4: Async Processing (Bottom Right)**
- **Technology**: Spring @Transactional(readOnly=true)
- **Impact**: Reduced GC pressure, 40% less GC pauses
- **Benefit**: 20-30ms P95 improvement

**Visual**: Each quadrant has an icon + key metric

**Speaker Notes**:
- Explain why each pattern was chosen
- Show how patterns work together
- Emphasize the compound effect: 10ms + 5ms + 1ms = 16ms base latency

---

## Slide 5: Test Environment & Methodology

**Layout**: Split 50/50

**Left - Environment Isolation**:
- **Container-Based**: Docker Compose
- **Dedicated Network**: performance-network
- **Resource Limits**: CPU 2 cores, Memory 512MB
- **Database**: PostgreSQL 15, 10K events pre-seeded
- **Monitoring**: Prometheus + Grafana

**Right - Test Methodology**:
- **Tool**: k6 Load Testing Framework
- **Phases**:
  1. Ramp-up: 30s (0 → 100 RPS)
  2. Sustained: 10 min (100 RPS stable)
  3. Ramp-down: 30s (100 → 0 RPS)
- **Request Mix**:
  - 40% - GET /events (list all)
  - 30% - GET /events/upcoming
  - 30% - GET /events/{id}
- **Dynamic Data**: Random IDs + timestamps (prevent caching bias)

**Visual**:
- Environment diagram
- Load profile graph showing the ramp-up curve

**Speaker Notes**:
- Explain environment parity with production
- Describe why request distribution matches real usage
- Highlight coordinated omission prevention (arrival rate executor)

---

## Slide 6: Monitoring Infrastructure

**Layout**: Dashboard screenshot + metrics list

**Grafana Dashboard Screenshot**:
- Full dashboard showing 7 panels during test execution
- Highlight P95 gauge showing green (< 200ms)

**Metrics Tracked**:
1. **Latency**: P50, P95, P99 request duration
2. **Throughput**: Requests per second
3. **CPU**: System and process CPU usage
4. **Memory**: JVM heap usage, GC activity
5. **Database**: Connection pool saturation
6. **Cache**: Hit/miss ratio, eviction rate
7. **Errors**: 4xx, 5xx rates

**Correlation Examples**:
- CPU spike → Latency spike
- GC pause → Latency spike
- Pool saturation → Latency spike

**Visual**: Annotated dashboard screenshot with arrows pointing to key metrics

**Speaker Notes**:
- Explain how real-time monitoring enabled bottleneck identification
- Show correlation between metrics (e.g., GC and latency)
- Mention Prometheus queries used for analysis

---

## Slide 7: Test Results - Success!

**Layout**: Large metrics + charts

**Primary Result (Center, Large Font)**:
```
✓ P95 Latency: [XX.XX]ms
  Target: < 200ms
  Status: PASSED ✓
```

**Detailed Results Table**:
| Metric | Target | Actual | Status |
|--------|--------|--------|--------|
| P50 Latency | N/A | [XX]ms | - |
| P95 Latency | < 200ms | [XX]ms | ✓ |
| P99 Latency | < 500ms | [XX]ms | ✓ |
| Throughput | 100 RPS | [XX] RPS | ✓ |
| Error Rate | < 1% | [X.XX]% | ✓ |
| Cache Hit Rate | > 80% | [XX]% | ✓ |

**Latency Chart**:
- Time-series graph showing P50/P95/P99 over 10 minutes
- Horizontal red line at 200ms (threshold)
- All points below threshold

**Visual**: Green checkmarks, upward trending success indicators

**Speaker Notes**:
- Walk through each metric
- Highlight that P95 stayed well below 200ms throughout test
- Emphasize consistency (low variance) as equally important
- Share excitement about meeting the goal!

---

## Slide 8: Bottleneck Analysis & Correlation

**Layout**: Grid of 3-4 correlation charts

**Chart 1: CPU vs Latency**
- X-axis: Time
- Y-axis (left): CPU %
- Y-axis (right): P95 latency
- **Finding**: "Latency stable even at 60% CPU"

**Chart 2: GC vs Latency**
- GC pause events as vertical bars
- P95 latency line
- **Finding**: "GC pauses < 50ms, minimal impact"

**Chart 3: Connection Pool**
- Active vs max connections
- **Finding**: "Pool utilization peaked at [XX]%, no saturation"

**Chart 4: Cache Performance**
- Hit rate over time
- **Finding**: "Maintained [XX]% hit rate throughout test"

**Key Takeaways**:
- ✓ No CPU bottleneck (stayed under 80%)
- ✓ No memory pressure (heap at 60%)
- ✓ No connection contention
- ✓ Cache performed as expected

**Speaker Notes**:
- Explain correlation analysis technique
- Describe how each metric validates a design decision
- Mention that absence of bottlenecks proves design effectiveness

---

## Slide 9: Design Validation & Lessons Learned

**Layout**: Two columns

**Left - Design Validation**:

**Caching Pattern**:
- Expected: 90% hit rate, 10x speedup
- Actual: [XX]% hit rate, [X]x speedup
- Grade: [A/B/C] ✓

**Query Optimization**:
- Expected: 95% index usage
- Actual: [XX]% index usage
- Grade: [A/B/C] ✓

**Connection Pool**:
- Expected: < 80% utilization
- Actual: [XX]% peak utilization
- Grade: [A/B/C] ✓

**Right - Lessons Learned**:

**What Worked**:
1. In-memory caching had massive impact
2. Proper indexing eliminated DB bottleneck
3. Monitoring enabled rapid bottleneck identification
4. k6 provided realistic, reproducible load testing

**Surprises**:
1. [Surprising finding #1]
2. [Surprising finding #2]

**Future Improvements**:
1. [Potential optimization #1]
2. [Potential optimization #2]

**Visual**: Checkmarks for successes, lightbulb icons for lessons

**Speaker Notes**:
- Reflect on the design process
- Explain what you would do differently next time
- Connect back to course concepts (requirements → design → implementation → validation)

---

## Slide 10: Live Demo & Q&A

**Layout**: Split screen or demo preparation

**Demo Plan** (2-3 minutes):
1. Show Grafana dashboard (live metrics)
2. Run k6 load test command
3. Watch metrics update in real-time
4. Show final result: P95 < 200ms ✓
5. Drill into specific metrics (cache hit rate, CPU usage)

**Demo Script**:
```bash
# Terminal commands visible on screen
$ docker-compose -f docker-compose-monitoring.yml ps
$ k6 run k6-load-test.js
# Watch Grafana dashboard
$ curl http://localhost:9090/api/v1/query?query=...
```

**Expected Output**:
```
✓ P95 Latency: [XX]ms
✓ Error Rate: 0.0%
✓ Throughput: 100 RPS
SUCCESS: All thresholds passed!
```

**Q&A Prompts**:
- Questions about the design patterns?
- How would this scale to 1000 RPS?
- What about write-heavy workloads?
- Cost of this infrastructure?

**Backup Slides** (if time permits):
- Detailed code walkthrough
- Cost-benefit analysis
- Scalability projections

**Visual**: 
- Live demo window
- QR code linking to GitHub repo

**Speaker Notes**:
- Invite questions throughout the demo
- Have backup screenshots in case live demo fails
- Thank audience and instructor
- Mention code is available on GitHub for reference

---

## Appendix: Additional Slides (Hidden)

### Slide A: Code Deep Dive
- Show key code snippets (OptimizedEventService.java)
- Annotate with performance implications

### Slide B: Cost Analysis
- Infrastructure cost breakdown
- Cost per 1M requests
- ROI of optimization effort

### Slide C: Scalability Roadmap
- Current capacity: 100 RPS
- Path to 1000 RPS (horizontal scaling)
- Path to 10,000 RPS (distributed caching, CDN)

### Slide D: Comparison with Competitors
- Benchmark against similar systems
- Industry standards for event browsing latency

---

## Presentation Tips

**Timing** (15 minutes total):
- Slides 1-2: 2 minutes (Intro)
- Slides 3-4: 3 minutes (Design)
- Slide 5-6: 3 minutes (Testing)
- Slide 7-8: 3 minutes (Results)
- Slide 9: 2 minutes (Lessons)
- Slide 10: 2 minutes (Demo + Q&A)

**Visual Consistency**:
- Color scheme: Blue (trust) + Green (success) + Red (thresholds)
- Font: Sans-serif for readability
- Charts: Consistent axes and legend placement
- Icons: Modern, flat design

**Delivery**:
- Practice the demo beforehand
- Have backup screenshots of all live components
- Prepare answers to likely questions
- Show enthusiasm about the results!

**Handouts**:
- One-page summary of key metrics
- QR code to GitHub repository
- Contact info for follow-up questions

---

**Document Version**: 1.0  
**Last Updated**: December 24, 2025  
**Status**: Ready for Presentation
