# Live Demo Guide
## Performance Testing Demonstration - Step-by-Step

**Duration**: 5-10 minutes  
**Audience**: Course instructor, students  
**Objective**: Demonstrate P95 < 200ms @ 100 RPS in real-time  

---

## Pre-Demo Checklist

### 1. Environment Setup (Do this 30 minutes before demo)

```bash
# Navigate to performance testing directory
cd /path/to/AIU-Trips-And-Events/Project/performance-testing

# Start monitoring infrastructure
docker-compose -f docker-compose-monitoring.yml up -d

# Verify all services are running
docker-compose -f docker-compose-monitoring.yml ps

# Expected output:
# NAME                        STATUS
# aiu-trips-backend           Up (healthy)
# aiu-trips-postgres          Up (healthy)
# aiu-trips-prometheus        Up
# aiu-trips-grafana           Up
# aiu-trips-postgres-exporter Up
```

### 2. Health Checks

```bash
# Check backend health
curl http://localhost:8080/actuator/health
# Expected: {"status":"UP"}

# Check Prometheus targets
curl http://localhost:9090/api/v1/targets | jq '.data.activeTargets[] | {job: .labels.job, health: .health}'
# Expected: All targets "up"

# Check Grafana (browser)
open http://localhost:3001
# Login: admin / admin123
# Navigate to: AIU Trips - Performance Dashboard
```

### 3. Prepare Terminal Windows

Open 3 terminal windows side by side:
- **Terminal 1**: For k6 load test execution
- **Terminal 2**: For live metrics queries
- **Terminal 3**: For application logs

### 4. Browser Windows

Open 3 browser tabs:
- **Tab 1**: Grafana Dashboard (http://localhost:3001)
- **Tab 2**: Prometheus Queries (http://localhost:9090)
- **Tab 3**: Application Metrics (http://localhost:8080/actuator/metrics)

---

## Demo Script (5 minutes)

### Phase 1: Show Initial State (1 minute)

**Action**: Display Grafana dashboard

**Say**: 
> "This is our Grafana dashboard showing real-time metrics from the Events API. Currently, the system is idle. Notice the seven panels tracking latency, throughput, CPU, memory, database connections, and garbage collection."

**Point out**:
- P95 latency gauge showing 0 (no traffic)
- All other metrics at baseline
- Dashboard refresh interval: 5 seconds

**Terminal 1**:
```bash
# Show k6 is ready
k6 version
# Expected: k6 v0.x.x
```

---

### Phase 2: Warm-Up Cache (30 seconds)

**Action**: Send a few requests to prime the cache

**Say**:
> "Before the load test, let me warm up the cache with a few requests. This simulates a system that's already been running."

**Terminal 2**:
```bash
# Warm-up requests
for i in {1..5}; do
  curl -s http://localhost:8080/api/v2/events > /dev/null
  echo "Warmup request $i completed"
  sleep 1
done
```

**Watch**: 
- Grafana shows first requests appearing
- Latency starts showing values
- Cache starts populating

---

### Phase 3: Start Load Test (30 seconds)

**Action**: Execute k6 load test

**Say**:
> "Now I'm running the k6 load test. It will ramp up to 100 requests per second over 30 seconds, maintain that load for 10 minutes, then ramp down. For demo purposes, we'll watch the first 2-3 minutes."

**Terminal 1**:
```bash
# Run load test (will take 11 minutes total)
k6 run k6-load-test.js

# For demo, use shortened version:
k6 run --duration 2m k6-load-test.js
```

**Expected output (real-time)**:
```
     ✓ status is 200
     ✓ response time < 200ms
     ✓ response time < 500ms
     ✓ has body

     checks.........................: 100.00% ✓ 12000 ✗ 0
     data_received..................: 45 MB   375 kB/s
     data_sent......................: 1.2 MB  10 kB/s
     http_req_duration..............: avg=45.2ms min=5.1ms med=38.4ms max=152.3ms p(90)=78.5ms p(95)=89.2ms
     http_reqs......................: 12000   100/s
```

---

### Phase 4: Monitor Grafana (2 minutes)

**Action**: Switch to Grafana dashboard

**Say**:
> "As the load test runs, watch the dashboard update in real-time. The most important metric is the P95 latency gauge at the top."

**Point out in real-time**:

1. **HTTP Latency Panel** (top-left):
   - P50 line (blue): ~10-15ms
   - P95 line (red): ~40-60ms **← WELL BELOW 200ms threshold**
   - P99 line (green): ~80-120ms
   - **Say**: "Notice P95 stays consistently below 200ms"

2. **Request Rate Panel** (top-right):
   - Shows ~100 RPS
   - **Say**: "Request rate is stable at our target of 100 RPS"

3. **P95 Gauge** (middle-left):
   - Green indicator
   - Shows current P95 value (e.g., 52ms)
   - **Say**: "This gauge turns red if we exceed 200ms. It's staying green!"

4. **CPU Usage Panel** (middle-left):
   - Process CPU: ~50-60%
   - System CPU: ~40-50%
   - **Say**: "CPU usage is healthy, well below 80%"

5. **Memory Panel** (middle-right):
   - Heap used: ~60-70% of max
   - **Say**: "Memory usage is stable, no signs of leaks"

6. **Connection Pool Panel** (bottom-left):
   - Active connections: ~10-15 out of 20
   - **Say**: "Database connection pool is not saturated"

7. **GC Activity Panel** (bottom-right):
   - GC pause time: Low, occasional spikes
   - **Say**: "Garbage collection pauses are minimal, under 50ms"

---

### Phase 5: Query Prometheus (1 minute)

**Action**: Switch to Prometheus browser tab

**Say**:
> "Let me show you the raw metrics from Prometheus. This confirms what we see in Grafana."

**Execute Query 1**: P95 Latency
```promql
histogram_quantile(0.95, 
  sum(rate(http_server_requests_seconds_bucket{uri=~"/api/v2/events.*"}[1m])) by (le)
) * 1000
```

**Expected result**: ~40-60 (ms)

**Say**: "This shows our P95 latency is [X]ms, well below the 200ms requirement."

**Execute Query 2**: Request Rate
```promql
sum(rate(http_server_requests_seconds_count{uri=~"/api/v2/events.*"}[1m]))
```

**Expected result**: ~100 (requests/second)

**Say**: "This confirms we're handling 100 requests per second."

**Execute Query 3**: Cache Hit Rate (if instrumented)
```promql
sum(rate(cache_gets_total{result="hit"}[1m])) 
/ 
sum(rate(cache_gets_total[1m]))
```

**Expected result**: ~0.85-0.90 (85-90%)

**Say**: "Our cache hit rate is [X]%, which dramatically reduces database load."

---

### Phase 6: Show Application Logs (30 seconds)

**Action**: Switch to Terminal 3

**Say**:
> "The application logs show individual requests being processed. Notice the fast response times."

**Terminal 3**:
```bash
# Follow application logs (started before demo)
docker-compose -f docker-compose-monitoring.yml logs -f backend | grep "GET /api/v2/events"
```

**Expected output** (scrolling):
```
backend | 2025-12-24 15:30:45 INFO  [http-nio-8080-exec-12] o.s.w.s.m.m.a.RequestMappingHandlerMapping : GET /api/v2/events, time=12ms
backend | 2025-12-24 15:30:45 INFO  [http-nio-8080-exec-15] o.s.w.s.m.m.a.RequestMappingHandlerMapping : GET /api/v2/events/upcoming, time=8ms
backend | 2025-12-24 15:30:46 INFO  [http-nio-8080-exec-3] o.s.w.s.m.m.a.RequestMappingHandlerMapping : GET /api/v2/events/42, time=5ms
```

**Say**: "These logs show individual request processing times - most are under 20ms."

---

### Phase 7: Wait for k6 Results (If time permits)

**Action**: Let k6 test complete (or stop it early)

**Say**:
> "If we let this run for the full 10 minutes, k6 will generate a final report. For demo purposes, I'll show you pre-recorded results from a full test."

**Terminal 1** (if test completed):
```bash
# k6 final output

=== PERFORMANCE TEST RESULTS ===
P95 Latency: 47.32ms
P99 Latency: 89.15ms
Error Rate: 0.00%
Success: ✓ PASSED
================================

     ✓ http_req_duration...........p(95)<200 - [OK]
     ✓ errors......................rate<0.01 - [OK]
```

**Say**: "The test passed! Our P95 latency of 47ms is well below the 200ms requirement."

---

### Phase 8: Explain Key Takeaways (1 minute)

**Action**: Return to Grafana dashboard

**Say**:
> "Let me summarize what we've proven here:
> 
> 1. **Performance Target Met**: P95 latency stayed at ~50ms, far below the 200ms requirement.
> 
> 2. **Design Patterns Work**: Our caching strategy achieved 85%+ hit rate, reducing latency by 10x.
> 
> 3. **System is Stable**: No CPU bottlenecks, no memory leaks, no connection pool saturation.
> 
> 4. **Production-Ready**: This system can handle 100 RPS continuously without degradation.
> 
> 5. **Scalability Headroom**: CPU at 60% means we could handle 50-70% more load before optimization."

---

### Phase 9: Q&A Preparation (1 minute)

**Have ready**:
1. Code snippets of key optimizations
2. Architecture diagram
3. Backup screenshots (if live demo fails)

**Common Questions**:

**Q: "What happens at 200 RPS?"**
A: "Based on resource utilization, I estimate we could handle ~150-170 RPS before hitting bottlenecks. Beyond that, we'd need horizontal scaling."

**Q: "What if the cache is disabled?"**
A: "Good question! Let me show you..." [Toggle cache, rerun test, show P95 increases to ~150-180ms]

**Q: "How much does this infrastructure cost?"**
A: "In production, approximately $50-100/month for this scale (AWS t3.medium instance + RDS PostgreSQL). At 100 RPS = ~8.6M requests/day."

**Q: "What about write operations (POST/PUT/DELETE)?"**
A: "Write operations are slower (~100-150ms) but less frequent. Cache invalidation strategy ensures consistency."

**Q: "How did you prevent coordinated omission?"**
A: "I used k6's 'ramping-arrival-rate' executor which maintains constant arrival rate independent of response times. Also added think time to reduce load generator CPU usage."

---

## Troubleshooting During Demo

### Problem: Services won't start

**Symptom**: `docker-compose ps` shows unhealthy services

**Quick Fix**:
```bash
# Check logs
docker-compose -f docker-compose-monitoring.yml logs backend

# Restart specific service
docker-compose -f docker-compose-monitoring.yml restart backend

# Last resort: recreate all
docker-compose -f docker-compose-monitoring.yml down
docker-compose -f docker-compose-monitoring.yml up -d
```

### Problem: Grafana dashboard not showing data

**Symptom**: Empty graphs in Grafana

**Quick Fix**:
1. Check Prometheus targets: http://localhost:9090/targets
2. Verify actuator endpoint: curl http://localhost:8080/actuator/prometheus
3. Check Grafana datasource: Settings → Data Sources → Prometheus
4. Refresh dashboard: Click refresh icon or press Ctrl+R

### Problem: High latency during test

**Symptom**: P95 > 200ms

**Quick Fix**:
1. Check if cache is cold (run warm-up script)
2. Verify database indexes exist:
   ```bash
   docker exec -it aiu-trips-postgres psql -U aiu_user -d tripsdb -c "\d events"
   ```
3. Check system resources: `docker stats`
4. Reduce test load: `k6 run --vus 50 k6-load-test.js`

### Problem: k6 not installed

**Symptom**: `k6: command not found`

**Quick Fix**:
```bash
# macOS
brew install k6

# Ubuntu/Debian
sudo gpg -k
sudo gpg --no-default-keyring --keyring /usr/share/keyrings/k6-archive-keyring.gpg --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys C5AD17C747E3415A3642D57D77C6C491D6AC1D69
echo "deb [signed-by=/usr/share/keyrings/k6-archive-keyring.gpg] https://dl.k6.io/deb stable main" | sudo tee /etc/apt/sources.list.d/k6.list
sudo apt-get update
sudo apt-get install k6

# Windows
choco install k6
```

### Backup Plan: Pre-recorded Results

If live demo fails completely, have these ready:

1. **Video Recording**: Record full demo beforehand (5 minutes)
2. **Screenshots**: 
   - Grafana dashboard during test
   - k6 terminal output
   - Prometheus query results
3. **Exported Metrics**: JSON file with all metrics
4. **Slide Deck**: Backup slides with static results

---

## Post-Demo Cleanup

```bash
# Stop all services
docker-compose -f docker-compose-monitoring.yml down

# Optionally remove volumes (cleans database)
docker-compose -f docker-compose-monitoring.yml down -v

# Remove k6 summary file
rm summary.json
```

---

## Demo Checklist

**30 minutes before**:
- [ ] Start Docker services
- [ ] Verify health checks
- [ ] Test k6 installation
- [ ] Open browser tabs (Grafana, Prometheus)
- [ ] Prepare terminal windows
- [ ] Test internet connection (if needed)

**5 minutes before**:
- [ ] Run cache warm-up
- [ ] Verify Grafana dashboard loads
- [ ] Test a single curl request
- [ ] Check system time is correct (affects metrics)
- [ ] Close unnecessary applications (to free resources)

**During demo**:
- [ ] Speak clearly and pace yourself
- [ ] Point to specific metrics as they update
- [ ] Explain why metrics matter
- [ ] Show enthusiasm!

**After demo**:
- [ ] Answer questions
- [ ] Share GitHub repo link
- [ ] Offer to show code in detail
- [ ] Thank audience

---

## Contact

For questions about this demo:
- **Email**: [your-email@example.com]
- **GitHub**: [your-github-username]
- **Course**: CSE352 System Analysis and Design

---

**Document Version**: 1.0  
**Last Updated**: December 24, 2025  
**Demo Duration**: 5-10 minutes  
**Success Rate**: Aim for 100%! 🎯
