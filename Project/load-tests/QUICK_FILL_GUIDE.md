# Quick Guide: Filling Your Performance Test Report

This guide helps you quickly extract and fill in the performance report template with your actual test results.

## 📋 Preparation Checklist

Before running tests, ensure:
- [ ] All services are running (`./monitoring-manager.sh status`)
- [ ] Backend is healthy (`curl http://localhost:8080/actuator/health`)
- [ ] Grafana is accessible (http://localhost:3001)
- [ ] You've chosen a component to test (e.g., Events List, Bookings, Auth)

## 🚀 Quick Workflow

### Step 1: Run Your Test (5 minutes)
```bash
cd Project/load-tests
./run-tests.sh

# Or run a specific test
k6 run scripts/events-list-test.js
```

### Step 2: Extract Key Numbers (2 minutes)

#### From k6 Output
Look at the terminal output or `results/[TIMESTAMP]/*.log`:

```
http_req_duration..............: avg=85.32ms  min=45.12ms  med=78.45ms  max=234.56ms  p(90)=120.34ms p(95)=145.67ms p(99)=189.23ms
http_reqs......................: 30000   100.05/s
checks.........................: 100.00% ✓ 30000 ✗ 0
```

Extract these values:
- **Average Response Time:** 85.32 ms
- **Min Response Time:** 45.12 ms
- **P50 (Median):** 78.45 ms
- **P90:** 120.34 ms
- **P95:** 145.67 ms ⭐ (MOST IMPORTANT!)
- **P99:** 189.23 ms
- **Max:** 234.56 ms
- **Total Requests:** 30000
- **Average RPS:** 100.05
- **Success Rate:** 100.00%

### Step 3: Screenshot Grafana Dashboards (3 minutes)

1. Open http://localhost:3001
2. Go to "AIU Trips & Events - Performance Dashboard"
3. Set time range to your test duration
4. Take screenshots:
   - Response Time Percentiles panel
   - Request Rate panel
   - JVM Memory panel
   - Database Connection Pool panel

Save as:
```
load-tests/screenshots/
├── response-time.png
├── throughput.png
├── resources.png
└── prometheus.png
```

### Step 4: Get Resource Metrics from Grafana (3 minutes)

From the Grafana dashboard during your test:

**CPU Usage:**
- Average CPU: ~XX%
- Peak CPU: ~XX%

**Memory Usage:**
- Heap Used (Avg): ~XXX MB
- Heap Used (Peak): ~XXX MB
- Heap Utilization: ~XX%

**GC Metrics:**
- GC Collections (Young): XX
- Avg GC Pause: XX.XX ms

**Database:**
- Active Connections (Peak): X
- Connection Pool Utilization: XX%

### Step 5: Fill the Report Template (10 minutes)

```bash
cd load-tests
cp PERFORMANCE_REPORT_TEMPLATE.md MY_EVENTS_REPORT.md
# Open MY_EVENTS_REPORT.md in your editor
```

#### Quick Find & Replace

Use your editor's find & replace (Ctrl+H or Cmd+H):

1. **Basic Info:**
   - `[COMPONENT_NAME]` → `Events List API`
   - `[TEST_DATE]` → `2024-01-15`
   - `[YOUR_NAME]` → `John Doe`

2. **Response Times (from k6):**
   - `[XX.XX] ms` (P50) → `78.45 ms`
   - `[XXX.XX] ms` (P95) → `145.67 ms`
   - `[XXX.XX] ms` (P99) → `189.23 ms`
   - `[XX.XX] ms` (Average) → `85.32 ms`

3. **Throughput:**
   - `[XXX.XX] RPS` → `100.05 RPS`
   - `[XXXXX]` (Total Requests) → `30000`

4. **Success Metrics:**
   - `[XX.XX]%` (Success Rate) → `100.00%`
   - `[X.XX]%` (Error Rate) → `0.00%`

5. **Status:**
   - `[PASS/FAIL]` → `PASS` (if P95 < 200ms)
   - `✅ / ❌` → `✅` (for passed metrics)

## 📊 Section-by-Section Guide

### Section 1: Executive Summary
✅ **Auto-filled** - Just update the numbers from k6 output

### Section 2: Component Overview
✏️ **Manual** - Describe your component:
- What endpoint you tested
- What it does
- Database operations involved

Example:
```markdown
**Endpoint:** `GET /api/events`
**Purpose:** Retrieves a paginated list of all events with their details

**Database Operations:**
- 1 SELECT query to fetch events
- Uses indexed columns (status, date)
- Returns typical 20 events per page
```

### Section 3: Low-Latency Design Patterns

For each pattern, fill in what YOU implemented:

#### 3.1 Caching
If you used caching:
```markdown
**Cache Technology:** Spring Cache with Caffeine
**Cache Hit Ratio:** 85% (from logs)
**Cached Data:** Event listings with 5-minute TTL
```

If NOT used:
```markdown
**Not Implemented** - Events data changes frequently, caching not applicable
```

#### 3.2 Database Optimization
**Always fill this** - check your repository:
```java
// Check for @EntityGraph, JOIN FETCH
@Query("SELECT e FROM Event e JOIN FETCH e.creator WHERE e.status = :status")
```

Document what you found.

#### 3.3 Connection Pool
**Check application.properties:**
```properties
spring.datasource.hikari.maximum-pool-size=20
```

Fill those values in.

### Section 4: Framework Optimizations

#### Spring Boot
**Already configured** - Document what's in `application.properties`:
```markdown
**Actuator Configuration:**
- Metrics enabled: ✅
- Prometheus export: ✅
- Health checks: ✅
```

#### JPA/Hibernate
**Check your code for:**
- Batch size settings
- Lazy loading
- Query optimization

#### JVM
**From docker-compose.yml:**
```yaml
JAVA_OPTS: >-
  -Xms512m -Xmx1024m
  -XX:+UseG1GC
  -XX:MaxGCPauseMillis=50
```

Document these settings.

### Section 7: Test Results

**Copy from k6 output directly** - This is your evidence!

### Section 8: Performance Analysis

**Identify bottlenecks from Grafana:**
- Look for spikes in response time
- Check if GC correlated with latency
- See if DB connections were saturated

Example:
```markdown
**Primary Bottleneck:** Database query time

**Evidence:**
- Database connection acquisition: 45ms (P95)
- Query execution time: 60ms average
- Network + serialization: 20ms

**Impact:** Database contributed ~65ms to total latency
```

### Section 10: Recommendations

Based on your results:

**If P95 < 200ms:** ✅
```markdown
1. Monitor in production
2. Set up alerts for P95 > 180ms
3. Continue current optimizations
```

**If P95 > 200ms:** ❌
```markdown
1. Add database query caching
2. Implement database read replicas
3. Add index on frequently queried columns
```

## 🎯 Priority Placeholders

Must fill (high priority):

1. **Executive Summary:** All metrics (5 minutes)
2. **Section 2:** Component description (2 minutes)
3. **Section 3:** At least 3 design patterns (5 minutes)
4. **Section 7:** Test results table (2 minutes)
5. **Section 8:** Bottleneck analysis (3 minutes)
6. **Appendix B:** Screenshots (already done)

Optional (if time permits):

- Detailed code examples
- Multiple optimization iterations
- Comparative analysis
- Extended recommendations

## ⚡ Speed Tips

### 1. Use Your IDE's Multi-Cursor
Select all `[XX.XX]` and replace at once:
- VS Code: Ctrl+D (Cmd+D on Mac) repeatedly
- IntelliJ: Alt+J repeatedly

### 2. Extract Metrics with Scripts

```bash
# Extract P95 from k6 log
cat results/[TIMESTAMP]/*.log | grep "p(95)" | awk '{print $2}'

# Get metrics from Prometheus
curl -G 'http://localhost:9090/api/v1/query' \
  --data-urlencode 'query=histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[5m])) by (le))'
```

### 3. Template Pre-Fill Script

Create `fill-basics.sh`:
```bash
#!/bin/bash
COMPONENT="Events List"
DATE=$(date +%Y-%m-%d)
NAME="Your Name"

sed -i "s/\[COMPONENT_NAME\]/$COMPONENT/g" MY_REPORT.md
sed -i "s/\[TEST_DATE\]/$DATE/g" MY_REPORT.md
sed -i "s/\[YOUR_NAME\]/$NAME/g" MY_REPORT.md
```

## 📸 Screenshot Checklist

Essential screenshots to capture:

- [ ] Grafana: Response Time Percentiles (with P95 line visible)
- [ ] Grafana: Request Rate showing 100 RPS plateau
- [ ] Grafana: JVM Memory (showing heap usage)
- [ ] Prometheus: Query for P95 latency
- [ ] (Optional) k6 terminal output

## ✅ Final Checklist

Before submitting your report:

- [ ] All [PLACEHOLDER] text replaced
- [ ] Executive Summary filled with actual numbers
- [ ] Component description is clear
- [ ] At least 3 design patterns documented
- [ ] Test results section complete
- [ ] Screenshots added and referenced
- [ ] Conclusion states PASS/FAIL clearly
- [ ] Your name and date filled in
- [ ] File saved with descriptive name (e.g., `Events_Performance_Report.md`)

## 🎓 Grading Focus Areas

Based on the assignment, ensure you cover:

1. **Response Time < 200ms** ⭐⭐⭐
   - Clearly show P95 metric
   - PASS or FAIL statement

2. **Design Patterns** ⭐⭐⭐
   - Document what you implemented
   - Explain impact on performance

3. **Testing Methodology** ⭐⭐
   - Show 100 RPS sustained load
   - Explain test configuration

4. **Analysis** ⭐⭐
   - Identify bottlenecks
   - Correlate with monitoring data

5. **Professional Presentation** ⭐
   - Well-formatted
   - Complete documentation
   - Evidence-based conclusions

## 💡 Example: Filled Section

**Before:**
```markdown
### Key Results
- **P95 Response Time:** [XXX.XX] ms
- **Success Rate:** [XX.XX]%
```

**After:**
```markdown
### Key Results
- **P95 Response Time:** 145.67 ms ✅
- **Success Rate:** 100.00% ✅

The component successfully met the P95 < 200ms requirement with 145.67ms,
providing a 27% margin below the threshold.
```

---

**Time to Complete:** ~30-45 minutes total  
**Difficulty:** Easy with this guide  
**Result:** Professional, comprehensive performance report ✨
