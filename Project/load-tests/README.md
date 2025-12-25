# Events List API Performance Testing

This directory contains performance testing infrastructure and results for the Events List API endpoint.

## 📁 Contents

- **SETUP_GUIDE.md** - Step-by-step instructions to run performance tests
- **EVENTS_LIST_PERFORMANCE_REPORT.md** - Complete performance analysis report (academic submission)
- **scripts/** - k6 load testing scripts
  - `events-list-test.js` - Events List endpoint test
- **results/** - Test results and metrics
  - `events-list_summary.json` - Test summary with metrics
  - `events-list-report.md` - Initial test report
- **screenshots/** - Visual evidence from monitoring dashboards
  - CPU Usage monitoring
  - Database Connection metrics
  - Docker Container metrics
  - Garbage Collection patterns
  - JVM Memory usage
  - K6 test execution
  - RPS (Requests Per Second) patterns
  - Response time by endpoint
  - Thread count dynamics

## 🚀 Quick Start

1. **Start the application:**
   ```bash
   cd Project
   ./monitoring-manager.sh start  # Linux/Mac
   # OR
   .\monitoring-manager.ps1 start  # Windows
   ```

2. **Run the performance test:**
   ```bash
   cd load-tests
   k6 run scripts/events-list-test.js --out json=results/events-list.json --summary-export=results/events-list_summary.json
   ```

3. **View the report:**
   - Open `EVENTS_LIST_PERFORMANCE_REPORT.md`

## 📊 Test Configuration

- **Endpoint:** GET /api/events
- **Target:** P95 < 200ms @ 100 RPS
- **Duration:** ~6.5 minutes
- **Tool:** k6 load testing

## 📝 For Academic Submission

Submit the following files:

1. **EVENTS_LIST_PERFORMANCE_REPORT.md** - Complete performance report with visual evidence
2. **screenshots/** - All 10 monitoring screenshots (visual evidence)
3. **results/events-list_summary.json** - Test metrics
4. **SETUP_GUIDE.md** - Setup instructions

**Key Features of the Report:**
- 80+ pages of comprehensive analysis
- 10 monitoring screenshots with detailed correlation analysis
- Cross-metric validation across all system dimensions
- Test script correlation with visual patterns
- Production-ready monitoring baselines

## 📖 Documentation

- **SETUP_GUIDE.md** - Complete setup and execution instructions
- **EVENTS_LIST_PERFORMANCE_REPORT.md** - Comprehensive performance analysis

---

**Status:** ✅ Test completed with comprehensive data collection. Report includes actual test results, detailed analysis, and visual evidence from monitoring systems validating all performance claims.
