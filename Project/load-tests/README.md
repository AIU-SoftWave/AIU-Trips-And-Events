# Performance Load Testing Suite

This directory contains the complete performance testing infrastructure for the AIU Trips and Events system, designed to validate the low-latency requirement of **P95 response time < 200ms at 100 RPS**.

## 🎯 Quick Links

- **[Assignment Completion Guide](./ASSIGNMENT_COMPLETE.md)** ⭐ START HERE!
- **[Quick Fill Guide](./QUICK_FILL_GUIDE.md)** - 30-minute report workflow
- **[Setup Guide](./SETUP_GUIDE.md)** - Complete installation & usage
- **[Pattern Checklist](./PATTERN_CHECKLIST.md)** - What's implemented & what to add
- **[Framework Optimizations](./FRAMEWORK_OPTIMIZATIONS.md)** - Spring Boot, JPA, JVM, etc.

## 📁 Directory Structure

```
load-tests/
├── scripts/                     # k6 test scripts
│   ├── auth-login-test.js      # Authentication endpoint test
│   ├── events-list-test.js     # Events listing endpoint test
│   └── bookings-list-test.js   # Bookings endpoint test
├── results/                     # Test results (auto-generated)
│   └── [TIMESTAMP]/            # Results for each test run
│       ├── *.log               # Human-readable output
│       ├── *.json              # Raw metrics data
│       ├── *_summary.json      # Test summary
│       └── SUMMARY.md          # Auto-generated summary
├── screenshots/                 # Monitoring dashboard screenshots
├── run-tests.sh                # Test suite runner script
├── PERFORMANCE_REPORT_TEMPLATE.md  # Comprehensive report template
├── SETUP_GUIDE.md              # Complete setup instructions
└── README.md                   # This file
```

## 🎯 Quick Start

### 1. Start the Monitoring Stack
```bash
cd ../  # Go to Project directory
docker-compose up -d
```

### 2. Verify Services
```bash
# Check backend health
curl http://localhost:8080/actuator/health

# View Grafana (admin/admin123)
open http://localhost:3001
```

### 3. Run Tests
```bash
cd load-tests
./run-tests.sh
```

### 4. View Results
```bash
# Latest results directory
cd results/$(ls -t results/ | head -1)
cat SUMMARY.md
```

## 📊 What Gets Tested

### Test Scenarios

| Test Name | Endpoint | Target | Threshold | Duration |
|-----------|----------|--------|-----------|----------|
| Auth Login | POST /api/auth/login | 100 RPS | P95 < 200ms | 5 min |
| Events List | GET /api/events | 100 RPS | P95 < 200ms | 5 min |
| Bookings List | GET /api/bookings/my-bookings | 100 RPS | P95 < 200ms | 5 min |

### Load Pattern

```
RPS
100 ├─────────────────────────────────────────────┐
    │                                              │
 50 ├───────────┐                                  │
    │           │                                  │
  0 └───────────┴──────────────────────────────────┴──→
    0s         60s                               360s  390s
      Ramp-up    Sustained Load (5 min)         Ramp-down
```

## 🔧 Available Test Scripts

### 1. Authentication Test (`auth-login-test.js`)
- **Purpose:** Validate login endpoint performance
- **Workload:** 100 RPS sustained for 5 minutes
- **Key Metrics:** Response time, token generation speed

### 2. Events List Test (`events-list-test.js`)
- **Purpose:** Validate event listing performance
- **Workload:** 100 RPS sustained for 5 minutes
- **Key Metrics:** Response time, database query performance

### 3. Bookings List Test (`bookings-list-test.js`)
- **Purpose:** Validate user bookings retrieval
- **Workload:** 100 RPS sustained for 5 minutes
- **Key Metrics:** Response time, authorization overhead

## 📈 Monitoring Stack

### Services Running

| Service | Port | Purpose | URL |
|---------|------|---------|-----|
| Grafana | 3001 | Visualization | http://localhost:3001 |
| Prometheus | 9090 | Metrics DB | http://localhost:9090 |
| Backend | 8080 | Application | http://localhost:8080 |
| cAdvisor | 8081 | Container metrics | http://localhost:8081 |
| Node Exporter | 9100 | System metrics | http://localhost:9100 |
| Postgres Exporter | 9187 | DB metrics | http://localhost:9187 |

### Key Dashboards

**Grafana Dashboard:** "AIU Trips & Events - Performance Dashboard"
- Request Rate (RPS)
- Response Time Percentiles (P50, P95, P99)
- JVM Memory Usage
- Garbage Collection
- Database Connection Pool
- CPU & Thread Metrics
- Error Rate

## 📝 Reporting

### 1. Generate Test Results

Run the test suite:
```bash
./run-tests.sh
```

This creates:
- Detailed logs in `results/[TIMESTAMP]/`
- JSON metrics data
- Auto-generated summary

### 2. Fill the Comprehensive Report

Use the template:
```bash
cp PERFORMANCE_REPORT_TEMPLATE.md MY_PERFORMANCE_REPORT.md
# Edit and fill in [PLACEHOLDER] values
```

The template includes:
- ✅ Executive Summary
- ✅ Component Overview
- ✅ Low-Latency Design Patterns (with placeholders)
- ✅ Framework Optimizations (Spring Boot, JPA, JVM, PostgreSQL, Next.js)
- ✅ Test Methodology
- ✅ Results Analysis (with placeholders)
- ✅ Bottleneck Analysis
- ✅ Recommendations
- ✅ Appendices (code samples, configurations, screenshots)

### 3. Add Screenshots

Take screenshots of Grafana panels:
```bash
mkdir -p screenshots
# Take screenshots and save to screenshots/
```

Required screenshots:
- `response-time.png` - Response time percentiles chart
- `throughput.png` - Request rate chart
- `resources.png` - CPU and memory usage
- `prometheus.png` - Prometheus metrics view

## 🎓 Assignment Requirements Coverage

This setup addresses all requirements from `../Extra/assginment.md`:

### ✅ Component Implementation
- Choose any component (Events, Bookings, Auth, etc.)
- Already optimized with design patterns

### ✅ Low-Latency Patterns
Report template includes sections for:
- Caching strategies
- Database query optimization
- Connection pool tuning
- Asynchronous processing
- Index optimization
- Response compression
- Pagination

### ✅ Framework Enhancements
Report covers:
- Spring Boot (Actuator, Auto-configuration, Tomcat tuning)
- JPA/Hibernate (Batch processing, Second-level cache)
- JVM (G1GC, heap tuning, GC optimization)
- PostgreSQL (Configuration, query optimization)
- Next.js (SSG, ISR, code splitting, caching)

### ✅ Testing Environment
Complete setup with:
- Isolated test environment
- Production-like configuration
- Comprehensive monitoring (Prometheus, Grafana, cAdvisor)
- Key metrics tracking (CPU, memory, GC, DB, network)

### ✅ Load Testing Tool
- k6 with percentile tracking
- 100 RPS sustained load
- P95 < 200ms threshold
- Ramp-up and sustained load periods

### ✅ Documentation
- Comprehensive report template
- Setup and execution guide
- Results analysis framework
- Troubleshooting guide

## 🚀 Advanced Usage

### Running Individual Tests

```bash
# Run with custom duration
k6 run --duration 10m scripts/events-list-test.js

# Run with custom VUs
k6 run --vus 50 scripts/events-list-test.js

# Run with custom base URL
BASE_URL=http://production:8080 k6 run scripts/events-list-test.js

# Run and save results
k6 run --out json=results/my-test.json scripts/events-list-test.js
```

### Custom Test Configuration

Edit test scripts to modify:
```javascript
export const options = {
  stages: [
    { duration: '30s', target: 50 },   // Adjust ramp-up
    { duration: '10m', target: 150 },  // Change sustained load
    { duration: '30s', target: 0 },    // Ramp-down
  ],
  thresholds: {
    'http_req_duration': ['p(95)<150'], // Stricter threshold
  },
};
```

### Analyzing Specific Metrics

```bash
# Extract P95 from k6 output
cat results/[TIMESTAMP]/*.log | grep "p(95)"

# Query Prometheus for specific time range
curl -G 'http://localhost:9090/api/v1/query_range' \
  --data-urlencode 'query=histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[1m])) by (le))' \
  --data-urlencode 'start=2024-01-01T10:00:00Z' \
  --data-urlencode 'end=2024-01-01T11:00:00Z' \
  --data-urlencode 'step=15s'
```

## 🐛 Troubleshooting

### Tests Failing?
1. Check backend is running: `curl http://localhost:8080/actuator/health`
2. Verify credentials: admin@aiu.edu / admin123
3. Check logs: `docker-compose logs backend`

### No Metrics in Grafana?
1. Check Prometheus targets: http://localhost:9090/targets
2. Verify actuator endpoint: `curl http://localhost:8080/actuator/prometheus`
3. Restart Prometheus: `docker-compose restart prometheus`

### High Latency?
1. Warm up the application first (run 100 requests)
2. Check CPU/memory usage in Grafana
3. Review GC pause times
4. Check database connection pool saturation

See `SETUP_GUIDE.md` for comprehensive troubleshooting.

## 📚 Documentation

- **[SETUP_GUIDE.md](./SETUP_GUIDE.md)** - Complete setup and execution instructions
- **[PERFORMANCE_REPORT_TEMPLATE.md](./PERFORMANCE_REPORT_TEMPLATE.md)** - Comprehensive report template
- **[../Extra/assginment.md](../Extra/assginment.md)** - Original assignment requirements

## 🎯 Success Criteria

Your test is successful when:
- ✅ P95 response time < 200ms
- ✅ Sustained 100 RPS for 5 minutes
- ✅ Error rate < 5%
- ✅ No service degradation during test
- ✅ Resources within acceptable limits (CPU < 80%, Memory < 85%)

## 💡 Tips

1. **Warm up first:** Run 100-500 requests before official test
2. **Clean state:** Start with fresh database for consistent results
3. **Monitor live:** Watch Grafana during test execution
4. **Multiple runs:** Run each test 3-5 times, use median results
5. **One at a time:** Don't run multiple tests simultaneously
6. **Document everything:** Take screenshots, save logs, note observations

## 🔗 References

- **k6 Documentation:** https://k6.io/docs/
- **Prometheus:** https://prometheus.io/docs/
- **Grafana:** https://grafana.com/docs/
- **Research Paper:** https://arxiv.org/pdf/2309.04259
- **Video Tutorial:** https://www.youtube.com/watch?v=q7qKeUVS4Gw

---

**Ready to test?** Start with `./run-tests.sh` 🚀
