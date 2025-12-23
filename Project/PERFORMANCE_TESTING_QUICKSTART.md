# Performance Testing - Quick Start 🚀

This is your one-page guide to completing the low-latency performance testing assignment.

## What You Need to Do

**Goal:** Test a component and prove P95 response time < 200ms @ 100 RPS

**Time Required:** 2-3 hours

**Deliverables:** 
1. Filled performance report
2. Test results and screenshots
3. PowerPoint presentation

---

## Step-by-Step Guide

### 1️⃣ Start the Infrastructure (10 min)

**Linux/Mac:**
```bash
cd Project

# Start all services (monitoring + application)
./monitoring-manager.sh start

# Verify everything is running
./monitoring-manager.sh health
```

**Windows (PowerShell):**
```powershell
cd Project

# Start all services (monitoring + application)
.\monitoring-manager.ps1 start

# Verify everything is running
.\monitoring-manager.ps1 health
```

**Windows (Command Prompt):**
```cmd
cd Project

REM Start all services (monitoring + application)
monitoring-manager.bat start

REM Verify everything is running
monitoring-manager.bat health
```

**📘 Windows Users:** See [load-tests/WINDOWS_GUIDE.md](./load-tests/WINDOWS_GUIDE.md) for detailed setup

**Services Started:**
- ✅ Backend (port 8080)
- ✅ Frontend (port 3000)
- ✅ Grafana Dashboard (port 3001)
- ✅ Prometheus (port 9090)
- ✅ Database + Exporters

### 2️⃣ Choose Your Component (2 min)

Pick ONE endpoint to test:

| Component | Endpoint | Good For |
|-----------|----------|----------|
| **Events List** | `GET /api/events` | Most common, good baseline |
| **Bookings** | `GET /api/bookings/my-bookings` | User-specific, auth required |
| **Authentication** | `POST /api/auth/login` | Fast, shows security overhead |

**Recommendation:** Start with Events List (easiest to analyze)

### 3️⃣ Run Performance Tests (5 min)

**Linux/Mac:**
```bash
cd load-tests

# Run all tests
./run-tests.sh

# Or run specific test
k6 run scripts/events-list-test.js
```

**Windows (PowerShell):**
```powershell
cd load-tests

# Run all tests
.\run-tests.ps1

# Or run specific test
k6 run scripts/events-list-test.js
```

**Windows (Command Prompt):**
```cmd
cd load-tests

REM Run all tests
run-tests.bat

REM Or run specific test
k6 run scripts/events-list-test.js
```

**What Happens:**
- Ramps up to 100 RPS over 60 seconds
- Sustains 100 RPS for 5 minutes
- Ramps down over 30 seconds
- Generates results in `results/[TIMESTAMP]/`

### 4️⃣ Collect Your Metrics (5 min)

#### From k6 Output

Look for these lines in the log:
```
http_req_duration..: avg=85.32ms min=45.12ms med=78.45ms max=234.56ms p(95)=145.67ms
http_reqs..........: 30000   100.05/s
checks.............: 100.00% ✓ 30000
```

**Write down:**
- P95: `______` ms (most important!)
- Average: `______` ms
- Total requests: `______`
- Success rate: `______` %

#### From Grafana Dashboard

1. Open http://localhost:3001 (login: admin/admin123)
2. Go to "AIU Trips & Events - Performance Dashboard"
3. Set time range to match your test
4. Take 4 screenshots:
   - Response Time Percentiles panel
   - Request Rate panel
   - JVM Memory panel
   - Database Connection Pool panel

Save screenshots to `load-tests/screenshots/`

### 5️⃣ Fill Your Report (45 min)

```bash
cd load-tests

# Copy the template
cp PERFORMANCE_REPORT_TEMPLATE.md MY_EVENTS_REPORT.md

# Open QUICK_FILL_GUIDE.md for instructions
# It shows exactly what to fill where
```

**Follow this guide:** [QUICK_FILL_GUIDE.md](./load-tests/QUICK_FILL_GUIDE.md)

**Use find & replace in your editor:**
- `[COMPONENT_NAME]` → Your component
- `[TEST_DATE]` → Today's date
- `[YOUR_NAME]` → Your name
- `[XX.XX] ms` → Your P50/P95 times
- `[PASS/FAIL]` → Based on your P95

### 6️⃣ Document Design Patterns (20 min)

**Use this guide:** [PATTERN_CHECKLIST.md](./load-tests/PATTERN_CHECKLIST.md)

**Already Implemented** (just document these):
1. ✅ Connection Pooling (HikariCP)
2. ✅ JVM Optimization (G1GC)
3. ✅ Database Indexes
4. ✅ Lazy Loading
5. ✅ RESTful API Design

**Quick Wins** (add if needed):
- Query Optimization (JOIN FETCH)
- Pagination
- Response Compression

**Where to Document:** Section 3 of your report

### 7️⃣ Add Framework Optimizations (15 min)

**Use this guide:** [FRAMEWORK_OPTIMIZATIONS.md](./load-tests/FRAMEWORK_OPTIMIZATIONS.md)

Copy relevant sections to your report:
- Spring Boot (Actuator, Tomcat tuning)
- JPA/Hibernate (HikariCP, query optimization)
- JVM (G1GC configuration)
- PostgreSQL (connection and query settings)

**Where to Document:** Section 4 of your report

---

## 📊 Success Criteria

Your report is complete when:

- ✅ All `[PLACEHOLDER]` text is replaced with actual values
- ✅ P95 metric is clearly stated (and hopefully < 200ms!)
- ✅ At least 3 design patterns documented with code examples
- ✅ Framework optimizations explained
- ✅ 4 Grafana screenshots included
- ✅ Conclusion states PASS or FAIL with explanation

---

## 🎓 For Your Presentation

Create a PowerPoint with:

**Slide 1:** Title and Component Chosen

**Slide 2:** Test Setup
- Docker monitoring stack
- k6 load testing
- 100 RPS sustained load

**Slide 3:** Results Summary
- P95: `______` ms ✅/❌
- Success Rate: `______` %
- Throughput: `______` RPS

**Slide 4:** Design Patterns
- List 3-5 patterns implemented
- Show code snippets
- Performance impact

**Slide 5:** Grafana Dashboard
- Screenshot of Response Time chart
- Screenshot of System Resources

**Slide 6:** Bottleneck Analysis
- What was the slowest part?
- What optimizations helped most?

**Slide 7:** Conclusion
- Met/Did not meet requirement
- Key learnings
- Future improvements

---

## 🆘 Troubleshooting

**Services won't start (Linux/Mac):**
```bash
docker-compose down -v
docker-compose up -d
```

**Services won't start (Windows):**
```powershell
docker-compose down -v
docker-compose up -d
```

**Tests fail:**
```bash
# Linux/Mac
curl http://localhost:8080/actuator/health

# Windows PowerShell
Invoke-WebRequest http://localhost:8080/actuator/health
# Should return {"status":"UP"}
```

**No data in Grafana:**
- Open http://localhost:9090/targets
- All should show "UP"
- Restart if needed: 
  - Linux/Mac: `./monitoring-manager.sh restart`
  - Windows: `.\monitoring-manager.ps1 restart` or `monitoring-manager.bat restart`

**Windows-specific issues:** See [load-tests/WINDOWS_GUIDE.md](./load-tests/WINDOWS_GUIDE.md)

**More help:** See [load-tests/SETUP_GUIDE.md](./load-tests/SETUP_GUIDE.md)

---

## 📚 All Documentation

| Guide | Purpose | Size | When to Use |
|-------|---------|------|-------------|
| **[ASSIGNMENT_COMPLETE.md](./load-tests/ASSIGNMENT_COMPLETE.md)** | Master guide | 12KB | Overview of everything |
| **[WINDOWS_GUIDE.md](./load-tests/WINDOWS_GUIDE.md)** | Windows setup | 8KB | Windows users |
| **[QUICK_FILL_GUIDE.md](./load-tests/QUICK_FILL_GUIDE.md)** | Fast workflow | 9KB | When filling report |
| **[SETUP_GUIDE.md](./load-tests/SETUP_GUIDE.md)** | Installation | 15KB | Setup and troubleshooting |
| **[PATTERN_CHECKLIST.md](./load-tests/PATTERN_CHECKLIST.md)** | Patterns guide | 10KB | Documenting patterns |
| **[FRAMEWORK_OPTIMIZATIONS.md](./load-tests/FRAMEWORK_OPTIMIZATIONS.md)** | Framework docs | 14KB | Framework section |
| **[PERFORMANCE_REPORT_TEMPLATE.md](./load-tests/PERFORMANCE_REPORT_TEMPLATE.md)** | Report template | 25KB | The actual report |

---

## ⚡ TL;DR Commands

**Linux/Mac:**
```bash
# Start everything
cd Project && ./monitoring-manager.sh start

# Run tests
cd load-tests && ./run-tests.sh

# View dashboard
open http://localhost:3001  # admin/admin123

# Fill report
cd load-tests
cp PERFORMANCE_REPORT_TEMPLATE.md MY_REPORT.md
# Then follow QUICK_FILL_GUIDE.md
```

**Windows (PowerShell):**
```powershell
# Start everything
cd Project; .\monitoring-manager.ps1 start

# Run tests
cd load-tests; .\run-tests.ps1

# View dashboard
Start-Process "http://localhost:3001"  # admin/admin123

# Fill report
cd load-tests
Copy-Item PERFORMANCE_REPORT_TEMPLATE.md MY_REPORT.md
# Then follow QUICK_FILL_GUIDE.md
```

---

## ✅ Final Checklist

Before submitting:

- [ ] Tests completed successfully
- [ ] P95 metric < 200ms (or documented why not)
- [ ] Report template filled completely
- [ ] At least 3 patterns documented with examples
- [ ] Framework optimizations section complete
- [ ] 4 Grafana screenshots added
- [ ] PowerPoint presentation created
- [ ] Demo prepared (can show Grafana live)

---

## 🎯 Expected Results

**If properly configured, you should achieve:**
- P50: 70-100ms
- P95: 120-180ms ✅ (under 200ms target!)
- Success Rate: >99%
- Throughput: 100 RPS sustained

**This proves:**
✅ Low-latency design patterns work  
✅ Framework optimizations are effective  
✅ System meets performance requirements  

---

**Ready? Start with:** `cd Project && ./monitoring-manager.sh start` 🚀

**Questions?** Check [ASSIGNMENT_COMPLETE.md](./load-tests/ASSIGNMENT_COMPLETE.md) for detailed explanations!
