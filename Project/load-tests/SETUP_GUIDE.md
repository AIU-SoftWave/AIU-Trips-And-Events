# Performance Testing Setup and Execution Guide

## Table of Contents
1. [Overview](#overview)
2. [Prerequisites](#prerequisites)
3. [Installation](#installation)
4. [Starting the Monitoring Stack](#starting-the-monitoring-stack)
5. [Running Performance Tests](#running-performance-tests)
6. [Accessing Monitoring Dashboards](#accessing-monitoring-dashboards)
7. [Analyzing Results](#analyzing-results)
8. [Troubleshooting](#troubleshooting)
9. [Best Practices](#best-practices)

---

## Overview

This guide provides step-by-step instructions for setting up and executing performance tests for the AIU Trips and Events system with comprehensive monitoring.

**Performance Goal:** P95 response time < 200ms at 100 RPS sustained load

**Components:**
- **Application:** Spring Boot Backend + Next.js Frontend
- **Database:** PostgreSQL with metrics export
- **Monitoring:** Prometheus + Grafana + cAdvisor + Node Exporter
- **Load Testing:** k6

---

## Prerequisites

### Software Requirements

1. **Docker & Docker Compose**
   - Docker Engine 20.x or higher
   - Docker Compose 2.x or higher
   
   ```bash
   # Verify installation
   docker --version
   docker-compose --version
   ```

2. **k6 Load Testing Tool**
   - Installation instructions: https://k6.io/docs/getting-started/installation/
   
   ```bash
   # macOS
   brew install k6
   
   # Linux (Debian/Ubuntu)
   sudo gpg -k
   sudo gpg --no-default-keyring --keyring /usr/share/keyrings/k6-archive-keyring.gpg --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys C5AD17C747E3415A3642D57D77C6C491D6AC1D69
   echo "deb [signed-by=/usr/share/keyrings/k6-archive-keyring.gpg] https://dl.k6.io/deb stable main" | sudo tee /etc/apt/sources.list.d/k6.list
   sudo apt-get update
   sudo apt-get install k6
   
   # Windows
   choco install k6
   
   # Verify installation
   k6 version
   ```

3. **System Requirements**
   - Minimum 4 CPU cores
   - Minimum 8 GB RAM
   - Minimum 20 GB free disk space

---

## Installation

### Step 1: Clone the Repository

```bash
git clone https://github.com/AIU-SoftWave/AIU-Trips-And-Events.git
cd AIU-Trips-And-Events/Project
```

### Step 2: Build Docker Images

```bash
# Build all services
docker-compose build

# Or build individually
docker-compose build backend
docker-compose build frontend
```

---

## Starting the Monitoring Stack

### Step 1: Start All Services

```bash
# Start all services in detached mode
docker-compose up -d

# Check service status
docker-compose ps
```

Expected output:
```
NAME                          STATUS    PORTS
aiu-trips-backend-main        Up        0.0.0.0:8080->8080/tcp
aiu-trips-frontend-main       Up        0.0.0.0:3000->3000/tcp
aiu-trips-db-main            Up        0.0.0.0:5433->5432/tcp
aiu-trips-prometheus         Up        0.0.0.0:9090->9090/tcp
aiu-trips-grafana            Up        0.0.0.0:3001->3000/tcp
aiu-trips-cadvisor           Up        0.0.0.0:8081->8080/tcp
aiu-trips-node-exporter      Up        0.0.0.0:9100->9100/tcp
aiu-trips-postgres-exporter  Up        0.0.0.0:9187->9187/tcp
```

### Step 2: Verify Services

#### Backend Health Check
```bash
curl http://localhost:8080/actuator/health
```

Expected response:
```json
{
  "status": "UP",
  "components": {
    "db": {"status": "UP"},
    "diskSpace": {"status": "UP"},
    "ping": {"status": "UP"}
  }
}
```

#### Prometheus Health Check
```bash
curl http://localhost:9090/-/healthy
```

#### Grafana Health Check
```bash
curl http://localhost:3001/api/health
```

### Step 3: View Logs

```bash
# View all logs
docker-compose logs -f

# View specific service logs
docker-compose logs -f backend
docker-compose logs -f prometheus
docker-compose logs -f grafana
```

---

## Running Performance Tests

### Step 1: Prepare the Application

1. **Warm up the application** (optional but recommended):
   ```bash
   # Send 100 requests to warm up caches and JIT
   for i in {1..100}; do
     curl -s http://localhost:8080/api/events > /dev/null
   done
   ```

2. **Verify test data exists:**
   - Login to the application at http://localhost:3000
   - Ensure there are events, users, and bookings in the database

### Step 2: Run Individual Tests

```bash
# Navigate to the load-tests directory
cd load-tests

# Run Events List test
k6 run scripts/events-list-test.js

# Run Bookings List test
k6 run scripts/bookings-list-test.js

# Run Authentication test
k6 run scripts/auth-login-test.js
```

### Step 3: Run Complete Test Suite

```bash
# Run all tests with the test runner script
./run-tests.sh

# Or specify custom base URL
BASE_URL=http://localhost:8080 ./run-tests.sh
```

### Step 4: Collect Results

Results are saved in `load-tests/results/[TIMESTAMP]/`:
- `*.log` - Human-readable test output
- `*.json` - Raw metrics data
- `*_summary.json` - Test summary statistics
- `SUMMARY.md` - Markdown summary report

---

## Accessing Monitoring Dashboards

### Grafana Dashboard

**URL:** http://localhost:3001  
**Username:** admin  
**Password:** admin123

**Pre-configured Dashboard:** "AIU Trips & Events - Performance Dashboard"

**Key Panels:**
1. **Request Rate (RPS)** - Shows requests per second with success/error breakdown
2. **Response Time Percentiles** - P50, P95, P99 latency over time
3. **Response Time by Endpoint** - Per-endpoint latency breakdown
4. **JVM Memory Usage** - Heap and non-heap memory utilization
5. **Garbage Collection** - GC pause frequency and duration
6. **Database Connection Pool** - Connection utilization
7. **CPU Usage** - Application and system CPU consumption
8. **Thread Count** - JVM thread metrics
9. **Error Rate** - Percentage of failed requests

### Prometheus Metrics

**URL:** http://localhost:9090

**Useful Queries:**

1. **P95 Response Time:**
   ```promql
   histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[1m])) by (le, uri))
   ```

2. **Request Rate:**
   ```promql
   sum(rate(http_server_requests_seconds_count[1m]))
   ```

3. **Error Rate:**
   ```promql
   (sum(rate(http_server_requests_seconds_count{status=~"5.."}[1m])) / sum(rate(http_server_requests_seconds_count[1m]))) * 100
   ```

4. **JVM Heap Usage:**
   ```promql
   (jvm_memory_used_bytes{area="heap"} / jvm_memory_max_bytes{area="heap"}) * 100
   ```

5. **Database Connection Pool Utilization:**
   ```promql
   (hikaricp_connections_active / hikaricp_connections_max) * 100
   ```

### cAdvisor (Container Metrics)

**URL:** http://localhost:8081

Shows detailed container-level metrics:
- CPU usage per container
- Memory usage per container
- Network I/O
- Disk I/O

### Node Exporter (System Metrics)

**URL:** http://localhost:9100/metrics

Raw system metrics including:
- CPU utilization
- Memory usage
- Disk I/O
- Network statistics

### Postgres Exporter (Database Metrics)

**URL:** http://localhost:9187/metrics

PostgreSQL-specific metrics:
- Active connections
- Database size
- Transaction rates
- Query performance

---

## Analyzing Results

### Step 1: Review k6 Output

```bash
# Open the latest test log
cd load-tests/results
ls -lt | head -2  # Find latest directory
cat [TIMESTAMP]/events-list_[TIMESTAMP].log
```

Look for:
```
     ✓ status is 200
     ✓ response time < 200ms
     ✓ response time < 500ms

     checks.........................: 100.00% ✓ 30000 ✗ 0
     data_received..................: 45 MB   150 kB/s
     data_sent......................: 3.6 MB  12 kB/s
     http_req_duration..............: avg=85.32ms  min=45.12ms  med=78.45ms  max=234.56ms  p(90)=120.34ms p(95)=145.67ms
     http_reqs......................: 10000   100.05/s
```

**Key Metrics to Extract:**
- `http_req_duration` → Response times (fill in report)
- `p(95)` → P95 latency (must be < 200ms)
- `http_reqs` → Total requests and RPS
- `checks` → Success rate (should be > 95%)

### Step 2: Review Grafana Dashboard

1. Open Grafana at http://localhost:3001
2. Navigate to "AIU Trips & Events - Performance Dashboard"
3. Set time range to match your test duration
4. Take screenshots of:
   - Response Time Percentiles panel
   - Request Rate panel
   - JVM Memory Usage panel
   - Database Connection Pool panel

### Step 3: Extract Prometheus Metrics

```bash
# Query P95 latency during test window
curl -G 'http://localhost:9090/api/v1/query' \
  --data-urlencode 'query=histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[5m])) by (le))'
```

### Step 4: Fill in the Report Template

1. Open `load-tests/PERFORMANCE_REPORT_TEMPLATE.md`
2. Replace all `[PLACEHOLDER]` values with actual numbers from your tests
3. Add screenshots to `load-tests/screenshots/` directory
4. Update paths in Appendix B to point to your screenshots

**Example Replacements:**
```markdown
# Before:
│ P95                     │ [XXX.XX] ms │ < 200 ms    │

# After:
│ P95                     │ 145.67 ms   │ < 200 ms    │
```

---

## Troubleshooting

### Issue: Backend Won't Start

**Symptoms:**
```
aiu-trips-backend-main exited with code 1
```

**Solutions:**
1. Check logs:
   ```bash
   docker-compose logs backend
   ```

2. Verify database is ready:
   ```bash
   docker-compose ps postgres
   ```

3. Restart services:
   ```bash
   docker-compose restart backend
   ```

---

### Issue: P95 Latency Above 200ms

**Possible Causes:**

1. **Cold Start / Cache Warming**
   - Solution: Warm up the application before testing
   - Run 100-500 requests before the actual test

2. **Database Connection Pool Saturation**
   - Check: `hikaricp_connections_active / hikaricp_connections_max`
   - Solution: Increase `spring.datasource.hikari.maximum-pool-size`

3. **Excessive Garbage Collection**
   - Check: GC pause frequency and duration in Grafana
   - Solution: Adjust JVM heap size or GC algorithm

4. **N+1 Query Problem**
   - Check: Database query count in logs
   - Solution: Use `@EntityGraph` or `JOIN FETCH`

5. **Slow Database Queries**
   - Check: `pg_stat_statements` in PostgreSQL
   - Solution: Add indexes, optimize queries

6. **Insufficient Resources**
   - Check: CPU and memory usage
   - Solution: Increase container limits or use more powerful hardware

---

### Issue: High Error Rate

**Possible Causes:**

1. **Authentication Failures**
   - Check: Test script credentials match admin user
   - Solution: Verify admin@aiu.edu / admin123

2. **Database Connection Timeout**
   - Check: `hikaricp_connections_acquire_seconds`
   - Solution: Increase connection pool size or timeout

3. **Resource Exhaustion**
   - Check: System metrics for CPU/memory limits
   - Solution: Scale resources or reduce load

---

### Issue: Prometheus Not Scraping Metrics

**Symptoms:**
- Empty graphs in Grafana
- "No data" messages

**Solutions:**
1. Verify backend actuator is accessible:
   ```bash
   curl http://localhost:8080/actuator/prometheus
   ```

2. Check Prometheus targets:
   - Open http://localhost:9090/targets
   - All targets should show "UP" status

3. Verify Prometheus configuration:
   ```bash
   docker-compose exec prometheus cat /etc/prometheus/prometheus.yml
   ```

4. Restart Prometheus:
   ```bash
   docker-compose restart prometheus
   ```

---

### Issue: Grafana Dashboard Not Loading

**Solutions:**
1. Check Grafana logs:
   ```bash
   docker-compose logs grafana
   ```

2. Verify data source connection:
   - Open http://localhost:3001/datasources
   - Test Prometheus connection

3. Re-import dashboard:
   - Go to Dashboards → Import
   - Upload `monitoring/grafana/dashboards/performance-dashboard.json`

---

### Issue: k6 Test Fails Immediately

**Possible Causes:**

1. **Backend Not Running**
   - Solution: Verify backend is up: `curl http://localhost:8080/actuator/health`

2. **Authentication Failure**
   - Solution: Check admin credentials in database
   - Reset admin password if needed

3. **Syntax Error in Test Script**
   - Solution: Check k6 error message
   - Validate JavaScript syntax

---

## Best Practices

### Before Testing

1. **Clean State**
   ```bash
   # Stop all services
   docker-compose down
   
   # Remove volumes (fresh database)
   docker-compose down -v
   
   # Start fresh
   docker-compose up -d
   ```

2. **Populate Test Data**
   - Create sufficient test data (at least 100 events, 50 users)
   - Ensure data distribution is realistic

3. **Warm-Up Period**
   - Run 100-500 requests before official test
   - Allow JVM JIT compiler to optimize

4. **Verify Monitoring**
   - Ensure all Grafana panels show data
   - Check Prometheus targets are healthy

### During Testing

1. **Single Test at a Time**
   - Don't run multiple k6 tests simultaneously
   - Wait for system to stabilize between tests

2. **Monitor in Real-Time**
   - Keep Grafana dashboard open
   - Watch for anomalies or spikes

3. **Avoid External Load**
   - Don't use the application manually during tests
   - Close unnecessary browser tabs

4. **Record Observations**
   - Note any unusual behavior
   - Screenshot interesting patterns

### After Testing

1. **Save Results Immediately**
   - Copy results directory to safe location
   - Backup database state if needed

2. **Export Metrics**
   - Take Grafana dashboard screenshots
   - Export Prometheus data for key metrics

3. **Document Findings**
   - Fill report template while fresh
   - Note any issues encountered

4. **Clean Up**
   ```bash
   # Stop services if not needed
   docker-compose stop
   
   # Or completely remove
   docker-compose down
   ```

### Testing Iterations

When optimizing and re-testing:

1. **Change One Thing at a Time**
   - Makes it easier to identify what works

2. **Document Changes**
   - Keep notes on what was modified
   - Include before/after metrics

3. **Consistent Test Conditions**
   - Same test data
   - Same test duration
   - Same system state

4. **Multiple Test Runs**
   - Run each test 3-5 times
   - Use median/average results
   - Identify outliers

---

## Quick Reference Commands

### Docker Management
```bash
# Start all services
docker-compose up -d

# Stop all services
docker-compose stop

# Remove all services and volumes
docker-compose down -v

# View logs
docker-compose logs -f [service_name]

# Restart a service
docker-compose restart [service_name]

# Check service status
docker-compose ps
```

### Testing Commands
```bash
# Run single test
k6 run scripts/[test-name].js

# Run all tests
./run-tests.sh

# Run with custom URL
BASE_URL=http://custom:8080 k6 run scripts/test.js
```

### Monitoring URLs
- **Application:** http://localhost:8080
- **Frontend:** http://localhost:3000
- **Grafana:** http://localhost:3001 (admin/admin123)
- **Prometheus:** http://localhost:9090
- **cAdvisor:** http://localhost:8081

### Health Checks
```bash
# Backend health
curl http://localhost:8080/actuator/health

# Prometheus health
curl http://localhost:9090/-/healthy

# Check backend metrics
curl http://localhost:8080/actuator/prometheus | grep http_server_requests
```

---

## Getting Help

### Resources
- **k6 Documentation:** https://k6.io/docs/
- **Prometheus Docs:** https://prometheus.io/docs/
- **Grafana Docs:** https://grafana.com/docs/
- **Spring Boot Actuator:** https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html
- **Assignment Reference:** `../Extra/assginment.md`

### Common Issues
- Check the Troubleshooting section above
- Review Docker logs for errors
- Verify all services are healthy
- Ensure sufficient system resources

---

**Last Updated:** [DATE]  
**Version:** 1.0
