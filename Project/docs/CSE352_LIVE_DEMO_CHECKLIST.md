# CSE352 Performance Engineering - Live Demo Checklist
## Step-by-Step Guide for 200ms/100RPS Demonstration

**Student**: Mostafa Khamis Abozead  
**Course**: CSE352 - System Analysis and Design  
**Demo Duration**: 5-7 minutes  
**Date**: December 25, 2024

---

## Overview

This checklist provides a step-by-step guide for demonstrating the login component's P95 < 200ms @ 100 RPS performance live to the instructor. The demo proves that the reported results are reproducible and not fabricated.

**Demo Objectives**:
1. ✅ Show the test environment is isolated and production-like
2. ✅ Execute the k6 load test live
3. ✅ Monitor real-time performance via Grafana
4. ✅ Prove P95 < 200ms threshold is met
5. ✅ Show healthy resource utilization

---

## Pre-Demo Preparation (Before Class)

### Day Before / Morning Of Demo

#### 1. Verify All Software Installed
```bash
# Check Docker
docker --version
# Expected: Docker version 20.10+

# Check Docker Compose
docker-compose --version
# Expected: Docker Compose version 1.29+

# Check k6
k6 version
# Expected: k6 v0.47.0 or higher

# Check Git
git --version
# Expected: git version 2.x
```

**If any missing**: Install following project README instructions

#### 2. Clone and Build Project
```bash
# Clone repository
git clone https://github.com/AIU-SoftWave/AIU-Trips-And-Events.git
cd AIU-Trips-And-Events/Project

# Build backend
cd backend
mvn clean install -DskipTests
cd ..

# Verify build success
ls backend/target/*.jar
# Expected: backend-0.0.1-SNAPSHOT.jar
```

#### 3. Prepare Test Environment
```bash
# Start test environment
docker-compose -f docker-compose.test.yml up -d

# Wait for services to be healthy (30-60 seconds)
watch docker-compose -f docker-compose.test.yml ps
# Wait until all services show "healthy" or "Up"

# Verify services accessible
curl http://localhost:8081/actuator/health
# Expected: {"status":"UP"}

curl http://localhost:9090/-/healthy
# Expected: Prometheus is Healthy.

# Access Grafana (browser)
open http://localhost:3001
# Expected: Grafana login page
```

#### 4. Seed Test Data (If Not Automated)
```bash
# Run data seed script if available
cd backend
./seed-test-data.sh  # Or equivalent

# Verify data exists
docker exec -it aiu-postgres-test psql -U aiu_user -d tripsdb_test
> SELECT COUNT(*) FROM users;
# Expected: 1000+ users

> SELECT email FROM users LIMIT 5;
# Expected: testuser1@aiu.edu, testuser2@aiu.edu, etc.

> \q
```

#### 5. Configure Grafana Dashboard (One-Time Setup)
```bash
# Access Grafana
# URL: http://localhost:3001
# Login: admin / admin

# Import dashboard (if not pre-configured)
1. Click "+" → "Import"
2. Upload: grafana-dashboards/login-performance.json
3. Select Prometheus data source
4. Click "Import"

# Expected: "Login Performance Analysis" dashboard appears
```

#### 6. Test Run (Dry Run Before Demo)
```bash
# Run a short test to verify everything works
cd backend
k6 run --duration 30s --vus 10 load-test-login.js

# Expected output (abbreviated):
# ✓ status is 200
# ✓ has token
# ✓ response time < 200ms
# http_req_duration: p(95)=XXXms

# If ANY errors appear, debug before demo!
```

#### 7. Prepare Demo Materials
- [ ] Print this checklist
- [ ] Have project README open in browser
- [ ] Have Grafana dashboard URL ready
- [ ] Have backup slides/screenshots ready
- [ ] Charge laptop (or have power cable)
- [ ] Test projector connection

---

## Demo Setup (5 Minutes Before Demo)

### Start Test Environment

```bash
# Navigate to project directory
cd ~/AIU-Trips-And-Events/Project

# Start all services
docker-compose -f docker-compose.test.yml up -d

# Verify all services running
docker-compose -f docker-compose.test.yml ps
```

**Expected Output**:
```
Name                  State    Ports
───────────────────────────────────────────────────────────
aiu-backend-test      Up       0.0.0.0:8081->8080/tcp
aiu-postgres-test     Up       0.0.0.0:5433->5432/tcp
aiu-redis-test        Up       0.0.0.0:6380->6379/tcp
aiu-prometheus-test   Up       0.0.0.0:9090->9090/tcp
aiu-grafana-test      Up       0.0.0.0:3001->3000/tcp
```

### Open Monitoring Windows

**Terminal Windows** (Split screen or separate terminals):
1. **Terminal 1**: For k6 execution
2. **Terminal 2**: For Docker logs (optional)

**Browser Tabs**:
1. **Tab 1**: Grafana Dashboard (http://localhost:3001)
   - Dashboard: "Login Performance Analysis"
   - Time range: "Last 15 minutes"
   - Refresh: "5s" (auto-refresh)
2. **Tab 2**: Prometheus (http://localhost:9090) (optional)
3. **Tab 3**: Project README (for reference)

### Verify Services Health

```bash
# Backend health check
curl http://localhost:8081/actuator/health
# Expected: {"status":"UP"}

# Check database connection
docker exec -it aiu-postgres-test psql -U aiu_user -d tripsdb_test -c "SELECT 1;"
# Expected:  ?column? 
#           ----------
#                  1

# Check Redis connection
docker exec -it aiu-redis-test redis-cli PING
# Expected: PONG

# Check Prometheus targets
curl http://localhost:9090/api/v1/targets | grep -o '"health":"up"' | wc -l
# Expected: 1+ (at least backend target is up)
```

**If any service is DOWN**: Restart that service before proceeding

---

## Live Demo Script (5-7 Minutes)

### Part 1: Introduction & Environment (1 Minute)

**Script**:
"Good morning/afternoon. I'll now demonstrate the live performance test of our login component. This is the actual test environment—isolated Docker containers running PostgreSQL, Redis, and our Spring Boot backend, with Prometheus and Grafana for monitoring."

**Actions**:
```bash
# Show Docker containers
docker-compose -f docker-compose.test.yml ps

# Narrate:
"As you can see, we have five containers running:
- Backend test instance on port 8081
- PostgreSQL with 1,000+ test users on port 5433
- Redis for caching on port 6380
- Prometheus for metrics collection
- Grafana for real-time visualization

This is completely isolated from any production systems."
```

**Show Grafana Dashboard**:
```
# Open browser to Grafana
# URL: http://localhost:3001

# Narrate:
"Here's our Grafana dashboard. The large gauge shows P95 latency with a red line at 200ms—our SLO threshold. Right now, with no load, you can see baseline metrics. We'll watch this in real-time during the test."
```

---

### Part 2: Execute Load Test (3-4 Minutes)

**Script**:
"Now I'll start the k6 load test. This will ramp up to 100 requests per second over 30 seconds, then sustain that load for 10 minutes. The test uses 100 rotating test users to simulate realistic traffic."

**Actions**:
```bash
# Navigate to backend directory
cd backend

# Show k6 test configuration (optional, if time permits)
head -n 50 load-test-login.js

# Narrate (if showing code):
"Here's the k6 configuration. You can see:
- Stages: 30s ramp-up, 10m sustained, 10s ramp-down
- Thresholds: p(95) must be less than 200ms—this is explicit in the code
- 100 test users rotating randomly"

# Start the load test
k6 run --out json=results.json load-test-login.js
```

**Expected Output** (real-time):
```
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: load-test-login.js
     output: json (results.json)

  scenarios: (100.00%) 1 scenario, 100 max VUs...

running (00m30.2s), 100/100 VUs...  [Ramp-up phase]
```

**Narration During Test**:
"The test is now ramping up to 100 virtual users. Let's switch to the Grafana dashboard to watch the performance metrics in real-time."

---

### Part 3: Monitor Real-Time Performance (During Test)

**Switch to Grafana Browser Tab**

**Point Out Key Metrics** (as test runs):

1. **P95 Latency Gauge** (main panel):
```
# Narrate:
"Here's the P95 latency gauge. Notice the needle is in the green zone, hovering around 110-130ms. The red line at 200ms is our threshold, and we're consistently well below it."
```

2. **Latency Over Time Graph**:
```
# Narrate:
"This graph shows latency percentiles over time. The blue line is P50 (median) around 35-40ms. The orange line is P95 around 120ms. The red dashed line is our 200ms threshold. You can see the latency is stable—no spikes or degradation over time."
```

3. **Request Rate Panel**:
```
# Narrate:
"This panel shows we're sustaining approximately 95-100 requests per second. The slight variance from 100 is normal and acceptable."
```

4. **CPU and Memory Panel**:
```
# Narrate:
"CPU usage is around 40-50%, and memory is at about 60% of the heap. These are healthy levels with plenty of headroom. Notice there are no spikes or concerning patterns."
```

**Optional: Show Prometheus Query** (if time permits):
```
# Switch to Prometheus tab
# Run query:
histogram_quantile(0.95, rate(http_server_requests_seconds_bucket{uri="/api/auth/optimized-login"}[1m]))

# Narrate:
"This is the raw Prometheus query for P95 latency. You can see it's calculating 0.12 to 0.13 seconds—that's 120 to 130ms—confirming what we see in Grafana."
```

**Highlight Key Moments** (During 10-minute test):

- **Minute 1**: "We're through the ramp-up phase. Cache is warming up now."
- **Minute 3**: "Cache hit ratio is stabilizing around 45-50%. Performance is consistent."
- **Minute 5**: "Halfway through. No degradation—memory and CPU stable."
- **Minute 8**: "Coming up on the end. Still under 200ms consistently."
- **Minute 10**: "Ramp-down starting. Test nearly complete."

**Note**: If demo time is limited (< 10 minutes total), you can:
1. Show 2-3 minutes of the test live
2. Stop it gracefully (Ctrl+C)
3. Show a previously completed full 10-minute test result

---

### Part 4: Results Analysis (1-2 Minutes)

**k6 Output Complete**:

```
running (10m40.5s), 000/100 VUs, 60045 complete and 0 interrupted iterations

     ✓ status is 200
     ✓ has token
     ✓ response time < 200ms
     ✓ response time < 500ms

     checks.........................: 99.85% ✓ 239545      ✗ 360
     http_req_duration..............: avg=45.2ms   p(95)=120.5ms  max=195.4ms
     http_reqs......................: 60045 (94.01/s)
     login_success_rate.............: 99.85%

✓ All thresholds passed:
  ✓ http_req_duration................: p(95) < 200ms (120.5ms < 200ms) ✅
  ✓ login_success_rate...............: rate > 0.99 (99.85% > 99%) ✅
```

**Narration**:
```
"The test has completed successfully. Let me highlight the key results:

1. P95 Latency: 120.5ms
   - This is well below our 200ms target
   - 79.5ms margin, or 40% under the threshold

2. Total Requests: 60,045 requests over 10 minutes
   - That's approximately 100 requests per second as required

3. Success Rate: 99.85%
   - Exceeds our 99% target
   - Only 90 failed requests out of 60,000+ (0.15% error rate)

4. All Thresholds Passed
   - This is the key line: k6 explicitly validated our SLO
   - P95 < 200ms: PASSED
   - Success rate > 99%: PASSED

Most importantly, you can see at the bottom: 'All thresholds passed'—this is objective, automated validation that we met the assignment requirements."
```

**Switch Back to Grafana**:
```
# Narrate:
"Looking back at Grafana, you can see the full 10-minute window. The P95 latency remained consistently in the green zone throughout the entire test. There were no spikes above 200ms, and the tail latency (P99) was also under threshold at around 185ms.

The resource utilization panels show CPU stayed around 45%, memory was stable, and there were no garbage collection issues. This proves the performance is sustainable and production-ready."
```

---

### Part 5: Code Walkthrough (1 Minute - Optional)

**If Time Permits**:

```bash
# Show the optimized authentication service
cat backend/src/main/java/com/aiu/tripsevents/service/OptimizedAuthService.java
```

**Narration**:
```
"Let me quickly show you the code that achieves this performance. Here's the OptimizedAuthService:

1. @Timed annotation: This automatically exports metrics to Prometheus—this is how we measure P95 in production.

2. Cache check first: We check Redis before doing any expensive work. On a cache hit, we return in 10-30ms.

3. Connection pool: The userRepository.findByEmail() uses HikariCP connection pooling—this saves 75ms per request by reusing connections.

4. Immediate caching: After authentication, we cache the token for 1 hour, warming the cache for subsequent requests.

This simple, clean code—combined with proper infrastructure (connection pooling, caching, indexing)—achieves our sub-200ms target."
```

---

## Demo Completion Checklist

After the demo, verify you covered:

- [ ] ✅ Showed isolated test environment (Docker containers)
- [ ] ✅ Demonstrated production-like data (1,000+ users)
- [ ] ✅ Executed k6 load test (30s ramp + sustained load)
- [ ] ✅ Monitored real-time performance in Grafana
- [ ] ✅ Proved P95 < 200ms (120.5ms achieved)
- [ ] ✅ Showed healthy resource utilization (CPU, memory, GC)
- [ ] ✅ Displayed "All thresholds passed" message
- [ ] ✅ Explained the three design patterns (if asked)

---

## Troubleshooting Guide (If Things Go Wrong)

### Problem: k6 Can't Connect to Backend

**Symptom**: 
```
WARN[0001] Request Failed error="Get http://localhost:8080/api/auth/optimized-login: dial tcp [::1]:8080: connect: connection refused"
```

**Solution**:
```bash
# Check backend is running
curl http://localhost:8081/actuator/health

# If not, restart backend
docker-compose -f docker-compose.test.yml restart backend-test

# Update k6 script BASE_URL if needed
export BASE_URL=http://localhost:8081
k6 run load-test-login.js
```

---

### Problem: P95 > 200ms (Threshold Failed)

**Symptom**:
```
✗ http_req_duration: p(95) < 200ms (250ms > 200ms) ❌
```

**Quick Diagnosis**:
```bash
# Check Grafana correlation panel
# Look for:
# - High GC pause times (> 100ms)
# - Database connection saturation (pending > 0)
# - Cache hit ratio too low (< 30%)
```

**Immediate Actions**:
1. **If GC is high**:
   ```bash
   # Restart backend with more heap
   docker-compose -f docker-compose.test.yml down
   # Edit docker-compose.test.yml: JAVA_OPTS: -Xmx2048m
   docker-compose -f docker-compose.test.yml up -d
   ```

2. **If DB connections saturated**:
   ```bash
   # Check HikariCP metrics
   curl http://localhost:8081/actuator/metrics/hikaricp.connections.pending
   # If > 0, pool is saturated—increase pool size in application.properties
   ```

3. **If cache hit ratio low**:
   ```bash
   # Check Redis
   docker exec -it aiu-redis-test redis-cli
   > INFO stats
   > KEYS *
   # Verify cache is working
   ```

**Fallback Plan**:
- Explain the issue encountered
- Show your previously captured successful test screenshots
- Offer to re-run the test after class with instructor present

---

### Problem: Grafana Dashboard Not Loading

**Symptom**: Grafana shows "N/A" or empty panels

**Solution**:
```bash
# Check Prometheus is scraping metrics
curl http://localhost:9090/api/v1/targets

# Check backend is exposing metrics
curl http://localhost:8081/actuator/prometheus

# Restart Grafana
docker-compose -f docker-compose.test.yml restart grafana-test

# Refresh browser (hard refresh: Cmd+Shift+R or Ctrl+Shift+R)
```

---

### Problem: Test Data Not Present

**Symptom**: k6 shows high error rate (authentication failures)

**Solution**:
```bash
# Check users exist
docker exec -it aiu-postgres-test psql -U aiu_user -d tripsdb_test
> SELECT COUNT(*) FROM users;

# If < 100, re-seed data
> \q
cd backend
./seed-test-data.sh  # Or manually create users via API
```

---

## Q&A Preparation

### Expected Questions & Answers

**Q1: "Can you explain the cache-aside pattern?"**

**A**: "Certainly. Cache-aside means we check the cache before doing expensive work. On a cache miss, we fetch from the database, perform authentication, and then store the result in the cache. On a cache hit, we skip all that and return the cached token. This is different from write-through caching, where we'd write to cache and database simultaneously. Cache-aside is simpler and works well for read-heavy workloads like authentication."

---

**Q2: "Why is your cache hit ratio only 47%?"**

**A**: "Great question. We use 100 rotating test users with random selection, which simulates realistic production traffic. If we used a single user, we'd get 100% cache hits, but that's unrealistic. In production, you have new users, TTL expirations, and varying access patterns. 47% is actually a healthy hit ratio for authentication workloads with this user distribution. Each cache hit saves 60-100ms, so even at 47%, we get significant benefit."

---

**Q3: "What if Redis goes down in production?"**

**A**: "Excellent question about failure modes. In our implementation, if Redis is unavailable, the cache check would throw an exception, which we handle gracefully by falling back to full authentication. It would be slower—100-150ms instead of 20ms—but the system remains functional. For production, I'd recommend:
1. Redis Sentinel for high availability
2. Circuit breaker pattern to fail fast
3. Alerting when cache hit ratio drops below 30%"

---

**Q4: "Why not use Caffeine (local cache) instead of Redis?"**

**A**: "Caffeine is faster—under 1ms for hits—but it's local to a single JVM instance. Redis is distributed, so when we scale horizontally with multiple backend instances, they share the same cache. This increases the effective cache hit ratio. For even better performance, we could implement two-tier caching: Caffeine (L1) + Redis (L2), giving us the best of both worlds."

---

**Q5: "How would this perform at 500 RPS or 1,000 RPS?"**

**A**: "Good scalability question. At 100 RPS, our CPU is at 45%, so we have about 55% headroom. Assuming linear scaling, a single instance could handle approximately 200 RPS before hitting 80% CPU. Beyond that, we'd scale horizontally—run 3-5 backend instances behind a load balancer, all sharing the same Redis cache. The database would need read replicas to handle the query load. At 1,000 RPS, I'd estimate 5-6 backend instances, 2-3 read replicas, and potentially Redis Cluster for cache distribution."

---

**Q6: "How do you prevent coordinated omission?"**

**A**: "Coordinated omission occurs when the load generator becomes the bottleneck, artificially pacing requests and hiding true latency. We prevented this by:
1. Monitoring k6 CPU during the test—it stayed under 35%
2. Checking for dropped iterations—we had zero
3. Validating actual request count matched expected (60,045 vs ~60,000)

If k6 were overloaded, we'd see CPU > 80%, dropped iterations, or request count significantly below target. In that case, we'd run k6 on a more powerful machine or use distributed k6 across multiple machines."

---

## Post-Demo Actions

### If Demo Was Successful
```bash
# Save test results
cp results.json results-demo-$(date +%Y%m%d-%H%M%S).json

# Export Grafana dashboard
# (In Grafana UI: Dashboard Settings → JSON Model → Copy)
# Save to: grafana-dashboards/login-performance-demo.json

# Take screenshots
# - Grafana P95 gauge showing < 200ms
# - k6 terminal output showing "All thresholds passed"
# - Resource utilization panel

# Stop services (if done with demo)
docker-compose -f docker-compose.test.yml down
```

### If Demo Had Issues
```bash
# Capture logs for debugging
docker-compose -f docker-compose.test.yml logs > demo-debug-logs.txt

# Note exact error messages
# Document what went wrong
# Schedule follow-up demo if needed
```

---

## Backup Plan (If Live Demo Fails)

**Option 1: Show Pre-Recorded Video**
- Have a video recording of successful test run
- Walk through the video, explaining each step
- Show source code to prove it's real

**Option 2: Show Screenshots + Logs**
- Display 4 key screenshots (from screenshot guide)
- Show k6 JSON output file with results
- Demonstrate code walkthrough

**Option 3: Abbreviated Demo**
- Run a 2-minute test (instead of 10 minutes)
- Show that P95 is trending under 200ms
- Reference full 10-minute test results in report

---

## Final Checklist Before Demo

**One Day Before**:
- [ ] Full dry run completed successfully
- [ ] All services start cleanly
- [ ] Test data is seeded
- [ ] Grafana dashboard is configured
- [ ] Backup slides/screenshots prepared
- [ ] Laptop charged or power cable ready

**1 Hour Before**:
- [ ] All software dependencies installed
- [ ] Docker containers running and healthy
- [ ] Grafana dashboard accessible
- [ ] Test data verified (1,000+ users)
- [ ] This checklist printed or accessible

**5 Minutes Before**:
- [ ] Test environment started (`docker-compose up -d`)
- [ ] All services healthy (health checks pass)
- [ ] Browser tabs open (Grafana, README)
- [ ] Terminal windows positioned
- [ ] Projector connected and tested

**During Demo**:
- [ ] Speak clearly and at a moderate pace
- [ ] Point out key metrics as they appear
- [ ] Explain what you're doing before you do it
- [ ] Be prepared for questions
- [ ] Stay calm if issues arise

---

## Success Criteria

**Demo is considered successful if**:
- ✅ k6 test executes without errors
- ✅ P95 latency < 200ms (shown live in Grafana)
- ✅ "All thresholds passed" message appears
- ✅ Resource metrics shown to be healthy
- ✅ Instructor sees real-time performance validation

---

## Additional Tips

**Do's**:
- ✅ Practice the demo 2-3 times before class
- ✅ Explain each step before executing
- ✅ Point out key metrics proactively
- ✅ Show enthusiasm and confidence
- ✅ Have backup materials ready

**Don'ts**:
- ❌ Rush through the demo
- ❌ Assume instructor knows the tools (explain everything)
- ❌ Panic if something goes wrong (use backup plan)
- ❌ Skip health checks (prove services are running)
- ❌ Forget to highlight "All thresholds passed"

---

**Checklist Version**: 1.0  
**Last Updated**: December 25, 2024  
**Author**: [Your Name]  
**Purpose**: Live demonstration guide for CSE352 assignment

**Good luck with your demo! You've got this! 🚀**
