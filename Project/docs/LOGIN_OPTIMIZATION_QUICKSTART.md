# Quick Start Guide: Login Performance Testing

## Prerequisites

- Docker and Docker Compose installed
- k6 load testing tool installed
- Java 17+ (for local development)
- Maven (for local development)

## Option 1: Docker-Based Testing (Recommended)

### Step 1: Start Test Environment

```bash
cd Project/backend

# Start all services (PostgreSQL, Redis, Backend, Prometheus, Grafana)
docker-compose -f docker-compose.test.yml up -d

# Wait for services to be healthy (takes ~30-60 seconds)
docker-compose -f docker-compose.test.yml ps

# Expected output: All services should show "healthy" status
```

### Step 2: Verify Services

```bash
# Check backend health
curl http://localhost:8081/actuator/health
# Expected: {"status":"UP"}

# Check Prometheus
curl http://localhost:9090/-/healthy
# Expected: Prometheus is Healthy.

# Open Grafana
# Browser: http://localhost:3001
# Login: admin / admin
```

### Step 3: Run Load Test

```bash
# Execute k6 load test
k6 run --out json=results.json load-test-login.js

# The test will:
# 1. Create 100 test users (setup phase)
# 2. Ramp up to 100 RPS over 30 seconds
# 3. Sustain 100 RPS for 10 minutes
# 4. Display real-time results
```

### Step 4: Monitor Results

**During Test**:
- Watch k6 terminal for real-time metrics
- Open Grafana: http://localhost:3001
  - Default dashboard shows P95 latency gauge
  - Cache hit ratio
  - Resource utilization

**After Test**:
- Check k6 summary output
- Look for: `✓ http_req_duration: p(95)<200`
- Review Grafana dashboard for detailed analysis

### Step 5: Cleanup

```bash
# Stop all services
docker-compose -f docker-compose.test.yml down

# Remove volumes (optional - clears all data)
docker-compose -f docker-compose.test.yml down -v
```

## Option 2: Local Development Testing

### Step 1: Start Dependencies Only

```bash
cd Project/backend

# Start only PostgreSQL and Redis
docker-compose -f docker-compose.test.yml up -d postgres-test redis-test

# Verify
docker-compose -f docker-compose.test.yml ps
```

### Step 2: Run Backend Locally

```bash
# Build
mvn clean install -DskipTests

# Run with test configuration
export SPRING_PROFILES_ACTIVE=test
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5433/tripsdb_test
export SPRING_DATA_REDIS_HOST=localhost
export SPRING_DATA_REDIS_PORT=6380

mvn spring-boot:run

# Backend will start on http://localhost:8080
```

### Step 3: Run Load Test

```bash
# In a new terminal
k6 run --env BASE_URL=http://localhost:8080 load-test-login.js
```

## Installing k6

### macOS
```bash
brew install k6
```

### Ubuntu/Debian
```bash
sudo gpg --no-default-keyring --keyring /usr/share/keyrings/k6-archive-keyring.gpg \
  --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys C5AD17C747E3415A3642D57D77C6C491D6AC1D69
echo "deb [signed-by=/usr/share/keyrings/k6-archive-keyring.gpg] \
  https://dl.k6.io/deb stable main" | sudo tee /etc/apt/sources.list.d/k6.list
sudo apt-get update
sudo apt-get install k6
```

### Windows
```powershell
choco install k6
```

## Understanding the Results

### k6 Output Interpretation

```
✓ status is 200                  99.8%
✓ has token                      99.8%
✓ response time < 200ms          95.2%
✓ response time < 500ms          99.9%

checks.........................: 99.8% ✓ 239520 ✗ 480
http_req_duration..............: avg=125ms p(95)=185ms p(99)=198ms
http_req_failed................: 0.1%
http_reqs......................: 60000 (100/s)
login_success_rate.............: 99.8%

✓ All thresholds passed
```

**What to Look For**:
- ✅ `p(95)=185ms` - P95 latency (must be <200ms)
- ✅ `http_reqs: 60000 (100/s)` - Sustained 100 RPS
- ✅ `login_success_rate: 99.8%` - High success rate
- ✅ `✓ All thresholds passed` - SLO achieved

### Grafana Dashboards

**Login Performance Dashboard**:
1. P95 Latency Gauge (top left)
   - Green: <150ms
   - Yellow: 150-200ms
   - Red: >200ms

2. Request Rate Graph
   - Should show ~100 RPS during sustained phase

3. Cache Hit Ratio
   - Expected: 40-60%

4. Resource Utilization
   - CPU: Should be <80%
   - Memory: Should be <80% of heap
   - GC Pause: Should be <100ms

## Troubleshooting

### Problem: Docker services not healthy

```bash
# Check logs
docker-compose -f docker-compose.test.yml logs backend-test
docker-compose -f docker-compose.test.yml logs postgres-test

# Common issues:
# - PostgreSQL not ready: Wait 30 seconds more
# - Redis connection refused: Check port 6380 is free
# - Backend startup errors: Check Java version (need 17+)
```

### Problem: k6 test fails immediately

```bash
# Check if backend is accessible
curl http://localhost:8081/actuator/health

# If not:
# - Ensure docker-compose is running
# - Check port 8081 is not in use
# - Review backend-test logs

# Try creating a user manually first
curl -X POST http://localhost:8081/api/auth/optimized-register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@aiu.edu","password":"Test123!","fullName":"Test User","phoneNumber":"555-0100"}'
```

### Problem: P95 > 200ms

**Diagnosis**:
1. Check Grafana "Correlating Spikes" panel
2. Look for correlated metrics:
   - High GC pause? Increase heap: `-Xmx2048m`
   - DB connection saturation? Increase pool size
   - Low cache hit ratio? Check Redis is working

**Quick Fixes**:
```bash
# Increase heap size
# Edit docker-compose.test.yml:
JAVA_OPTS: -Xms1024m -Xmx2048m

# Increase connection pool
# Edit application.properties:
spring.datasource.hikari.maximum-pool-size=40

# Restart services
docker-compose -f docker-compose.test.yml restart backend-test
```

### Problem: Coordinated Omission (k6 can't sustain 100 RPS)

```bash
# Check k6 machine CPU
top
# If k6 process >80% CPU, load generator is bottlenecked

# Solutions:
# 1. Run k6 on a more powerful machine
# 2. Use k6 cloud: k6 cloud run load-test-login.js
# 3. Reduce VU count, increase per-VU rate
```

## Expected Performance Baselines

### Healthy System
- P50: 50-100ms
- P95: 150-190ms
- P99: 190-200ms
- CPU: 40-60%
- Memory: 500-700MB / 1024MB
- GC Pause (P95): 30-50ms
- DB Connections: 8-15 / 20
- Cache Hit Ratio: 40-60%

### Warning Signs
- ⚠️ P95 > 180ms (approaching threshold)
- ⚠️ CPU > 70%
- ⚠️ GC Pause > 80ms
- ⚠️ DB Connections > 18 / 20 (90%+)
- ⚠️ Cache Hit Ratio < 30%

### Critical Issues
- 🔴 P95 > 200ms (SLO violation)
- 🔴 CPU = 100% (system saturated)
- 🔴 GC Pause > 150ms (impacting latency)
- 🔴 DB Connections = 20 / 20 (pool saturated)
- 🔴 Redis unavailable (cache-aside fallback only)

## Next Steps

After successful test run:

1. ✅ Review `LOGIN_OPTIMIZATION_REPORT.md` for detailed analysis
2. ✅ Review `LOGIN_OPTIMIZATION_PRESENTATION.md` for presentation prep
3. ✅ Save Grafana dashboard screenshots
4. ✅ Export k6 results: `k6 run --out json=results.json ...`
5. ✅ Document any issues encountered and fixes applied

## Support Resources

- **k6 Documentation**: https://k6.io/docs/
- **Prometheus Documentation**: https://prometheus.io/docs/
- **Grafana Documentation**: https://grafana.com/docs/
- **HikariCP Configuration**: https://github.com/brettwooldridge/HikariCP
- **Spring Boot Actuator**: https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html

---

**Questions?** Review the full technical report in `LOGIN_OPTIMIZATION_REPORT.md` for in-depth explanations.
