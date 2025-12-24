# Student Execution Checklist
## CSE352 Login Component Optimization Assignment

Use this checklist to ensure you complete all assignment requirements successfully.

---

## ✅ Phase 1: Understanding the Implementation (30 minutes)

- [ ] Read `LOGIN_OPTIMIZATION_README.md` - Get overview of project
- [ ] Read `LOGIN_OPTIMIZATION_ARCHITECTURE.txt` - Understand architecture
- [ ] Review the three low-latency design patterns:
  - [ ] Pattern #1: HikariCP Connection Pooling
  - [ ] Pattern #2: Redis Token Caching
  - [ ] Pattern #3: Optimized Authentication Flow
- [ ] Understand expected results: P95 < 200ms @ 100 RPS

---

## ✅ Phase 2: Environment Setup (30 minutes)

- [ ] Ensure Docker and Docker Compose are installed
  ```bash
  docker --version
  docker-compose --version
  ```

- [ ] Install k6 load testing tool
  - [ ] macOS: `brew install k6`
  - [ ] Linux: Follow instructions in Quick Start guide
  - [ ] Verify: `k6 version`

- [ ] Clone repository and navigate to backend
  ```bash
  cd Project/backend
  ```

- [ ] Review configuration files:
  - [ ] `docker-compose.test.yml` - Test environment
  - [ ] `prometheus.yml` - Metrics collection
  - [ ] `load-test-login.js` - Load testing script

---

## ✅ Phase 3: Execute Load Test (15 minutes)

- [ ] Start test environment
  ```bash
  docker-compose -f docker-compose.test.yml up -d
  ```

- [ ] Wait for services to be healthy (30-60 seconds)
  ```bash
  docker-compose -f docker-compose.test.yml ps
  ```
  - [ ] All services show "healthy" status

- [ ] Verify backend is accessible
  ```bash
  curl http://localhost:8081/actuator/health
  ```
  - [ ] Should return: `{"status":"UP"}`

- [ ] Open monitoring dashboards in browser:
  - [ ] Grafana: http://localhost:3001 (username: admin, password: admin)
  - [ ] Prometheus: http://localhost:9090

- [ ] Run k6 load test
  ```bash
  k6 run --out json=results.json load-test-login.js
  ```
  - [ ] Test runs for ~11 minutes (30s ramp-up + 10m sustained + 10s ramp-down)
  - [ ] Monitor real-time output in terminal
  - [ ] Watch Grafana dashboard for P95 latency

- [ ] Verify test completion
  - [ ] k6 shows: `✓ All thresholds passed`
  - [ ] Check P95 value: Should be < 200ms

---

## ✅ Phase 4: Capture Evidence (15 minutes)

### Screenshots to Capture:

- [ ] **Grafana Dashboard**
  - [ ] P95 Latency Gauge showing < 200ms (green zone)
  - [ ] Request Rate Graph showing ~100 RPS sustained
  - [ ] Cache Hit Ratio chart
  - [ ] Resource Utilization (CPU, Memory, GC)
  - [ ] Database Connection Pool usage

- [ ] **k6 Terminal Output**
  - [ ] Final summary showing:
    - [ ] `http_req_duration: p(95)=XXXms` (where XXX < 200)
    - [ ] `✓ All thresholds passed`
    - [ ] Success rate > 99%

- [ ] **Prometheus Metrics**
  - [ ] Query: `auth_login_time_seconds{quantile="0.95"}`
  - [ ] Shows P95 latency over time

### Export Results:

- [ ] Save k6 results JSON
  ```bash
  # Already saved as: results.json
  ```

- [ ] Export Grafana dashboard
  - [ ] Dashboard Settings → JSON Model → Copy

- [ ] Create results folder
  ```bash
  mkdir -p results/screenshots
  mv results.json results/
  # Move all screenshots to results/screenshots/
  ```

---

## ✅ Phase 5: Prepare Technical Report (1 hour)

- [ ] Open `LOGIN_OPTIMIZATION_REPORT.md`

- [ ] Fill in your actual results:
  - [ ] Section 1: Review pattern implementations (already complete)
  - [ ] Section 2: Review test environment (already complete)
  - [ ] Section 3: Review k6 script (already complete)
  - [ ] Section 4: Add your actual results:
    - [ ] Replace `[YOUR RESULT]` with actual P95 latency
    - [ ] Replace `[YOUR RESULT]` with actual throughput
    - [ ] Replace `[YOUR RESULT]` with actual success rate

- [ ] Add screenshots to report
  - [ ] Insert Grafana dashboard screenshots
  - [ ] Insert k6 terminal output screenshot
  - [ ] Insert Prometheus metrics screenshot

- [ ] Review "Correlating Spikes" section
  - [ ] If P95 > 200ms: Document root cause analysis
  - [ ] If P95 < 200ms: Document why it succeeded

- [ ] Proofread entire report
  - [ ] Check all sections are complete
  - [ ] Verify technical accuracy
  - [ ] Check grammar and formatting

---

## ✅ Phase 6: Prepare Presentation (1 hour)

- [ ] Open `LOGIN_OPTIMIZATION_PRESENTATION.md`

- [ ] Create PowerPoint slides (10 slides):
  - [ ] Slide 1: Title slide
  - [ ] Slide 2: Problem statement
  - [ ] Slide 3: Architecture overview
  - [ ] Slide 4: Pattern #1 (HikariCP)
  - [ ] Slide 5: Pattern #2 (Redis Cache)
  - [ ] Slide 6: Pattern #3 (Optimized Flow)
  - [ ] Slide 7: Test environment
  - [ ] Slide 8: Load testing methodology
  - [ ] Slide 9: Results (with your actual metrics)
  - [ ] Slide 10: Analysis & learnings

- [ ] Add visuals to slides:
  - [ ] Architecture diagram (from ARCHITECTURE.txt)
  - [ ] Performance comparison charts
  - [ ] Grafana dashboard screenshots
  - [ ] k6 results screenshots

- [ ] Practice presentation
  - [ ] Read speaker notes for each slide
  - [ ] Time yourself: 7-10 minutes target
  - [ ] Prepare answers for expected Q&A questions

---

## ✅ Phase 7: Prepare Demo (30 minutes)

- [ ] Review `LOGIN_OPTIMIZATION_QUICKSTART.md`

- [ ] Practice demo flow:
  1. [ ] Show docker-compose running
  2. [ ] Open Grafana dashboard
  3. [ ] Start k6 load test
  4. [ ] Switch between Grafana/k6 terminal during test
  5. [ ] Show final results

- [ ] Prepare backup plan:
  - [ ] Create screen recording of successful test run
  - [ ] Save all screenshots
  - [ ] Export k6 results JSON

- [ ] Test demo on your machine:
  - [ ] Clean environment: `docker-compose -f docker-compose.test.yml down -v`
  - [ ] Full run: Start → Test → Results
  - [ ] Verify everything works smoothly

---

## ✅ Phase 8: Code Walkthrough Prep (15 minutes)

- [ ] Review key implementation files:
  - [ ] `OptimizedAuthService.java` - Core service
  - [ ] `TokenCacheService.java` - Cache implementation
  - [ ] `RedisConfig.java` - Redis setup
  - [ ] `OptimizedAuthController.java` - Endpoint

- [ ] Understand code flow:
  - [ ] Cache check → Cache hit/miss
  - [ ] Authentication → BCrypt
  - [ ] Database query → HikariCP
  - [ ] Token generation → JWT
  - [ ] Caching → Redis

- [ ] Be ready to explain:
  - [ ] Why each pattern was chosen
  - [ ] How each pattern reduces latency
  - [ ] Trade-offs and considerations

---

## ✅ Phase 9: Final Checks (15 minutes)

### Documentation Completeness:

- [ ] All sections of technical report filled
- [ ] All screenshots captured and included
- [ ] Presentation slides complete with visuals
- [ ] Demo tested and working
- [ ] Results meet SLO: P95 < 200ms ✓

### Understanding Check:

Can you explain:
- [ ] Why HikariCP reduces latency by 75ms?
- [ ] How Redis cache-aside pattern works?
- [ ] What is coordinated omission and how to prevent it?
- [ ] How to correlate P95 spikes with GC/DB/CPU metrics?
- [ ] What to do if P95 > 200ms?

### Grading Criteria Review:

- [ ] **Section 1** (25 points): 3 patterns implemented ✓
- [ ] **Section 2** (25 points): Test environment with monitoring ✓
- [ ] **Section 3** (25 points): k6 script with correct profile ✓
- [ ] **Section 4** (25 points): Report + presentation + demo ✓

**Total**: 100 points - All criteria met ✓

---

## ✅ Phase 10: Submission (10 minutes)

### Files to Submit:

- [ ] **Technical Report**: `LOGIN_OPTIMIZATION_REPORT.md` (with your results)
- [ ] **Presentation**: PowerPoint file (10 slides)
- [ ] **Code**: 
  - [ ] `OptimizedAuthService.java`
  - [ ] `TokenCacheService.java`
  - [ ] `RedisConfig.java`
  - [ ] `OptimizedAuthController.java`
  - [ ] `load-test-login.js`
  - [ ] `docker-compose.test.yml`
- [ ] **Results**:
  - [ ] `results.json` (k6 output)
  - [ ] Screenshots folder
- [ ] **Documentation**:
  - [ ] `LOGIN_OPTIMIZATION_README.md`
  - [ ] `LOGIN_OPTIMIZATION_QUICKSTART.md`
  - [ ] `LOGIN_OPTIMIZATION_ARCHITECTURE.txt`

### Submission Checklist:

- [ ] All files organized in proper directory structure
- [ ] Code compiles without errors
- [ ] All documentation is readable and complete
- [ ] Screenshots are clear and labeled
- [ ] Results clearly show P95 < 200ms
- [ ] Presentation is polished and professional

---

## 🎯 Success Criteria

### Minimum Requirements (Pass):
- ✅ P95 < 200ms @ 100 RPS
- ✅ 3 low-latency patterns implemented
- ✅ Test environment with isolation
- ✅ k6 script with correct profile
- ✅ Complete documentation

### Excellence Criteria (A+):
- ✅ All minimum requirements met
- ✅ P95 < 180ms (good margin)
- ✅ Comprehensive monitoring (Prometheus + Grafana)
- ✅ Detailed root cause analysis capability
- ✅ Professional presentation
- ✅ Smooth demo execution
- ✅ Deep understanding demonstrated in Q&A

---

## 🚨 Troubleshooting

If you encounter issues, refer to:
- `LOGIN_OPTIMIZATION_QUICKSTART.md` - Section: Troubleshooting
- `LOGIN_OPTIMIZATION_REPORT.md` - Appendix B: Troubleshooting Guide

Common issues and solutions:
1. **Docker services not starting**: Check logs, wait longer
2. **P95 > 200ms**: Check Grafana for spike correlations
3. **k6 can't connect**: Verify backend is accessible
4. **Redis connection error**: Check Redis container is running

---

## 📞 Final Pre-Submission Checklist

- [ ] All code files included and organized
- [ ] Technical report complete with actual results
- [ ] Presentation polished and ready
- [ ] Demo tested and working
- [ ] Screenshots captured and labeled
- [ ] Results clearly show SLO achievement
- [ ] You can explain all design decisions
- [ ] You're ready for Q&A

**When all boxes are checked: You're ready to submit!** 🎉

---

**Assignment**: CSE352 - System Analysis and Design  
**Topic**: Low-Latency Component Implementation  
**Target**: P95 < 200ms @ 100 RPS  
**Status**: Ready for execution and submission

**Estimated Total Time**: 4-5 hours (from understanding to submission)

---

Good luck! You have everything you need to achieve excellent results. 🚀
