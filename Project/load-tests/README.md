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

1. **EVENTS_LIST_PERFORMANCE_REPORT.md** - Complete performance report
2. **results/events-list_summary.json** - Test metrics
3. **SETUP_GUIDE.md** - Setup instructions

## 📖 Documentation

- **SETUP_GUIDE.md** - Complete setup and execution instructions
- **EVENTS_LIST_PERFORMANCE_REPORT.md** - Comprehensive performance analysis

---

**Status:** Test completed with data collection. Report includes actual test results and analysis.
