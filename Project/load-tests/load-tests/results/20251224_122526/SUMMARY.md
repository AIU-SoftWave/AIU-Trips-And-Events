# Performance Test Summary

**Test Run:** 20251224_122526
**Base URL:** http://localhost:8080

## Results Overview

- **Total Tests:** 3
- **Passed:** 0
- **Failed:** 3

## Test Details

### 1. Authentication (Login) Test
- Script: `auth-login-test.js`
- Target: 100 RPS sustained for 5 minutes
- Threshold: P95 < 200ms
- Result: See `auth-login_20251224_122526.log`

### 2. Events List Test
- Script: `events-list-test.js`
- Target: 100 RPS sustained for 5 minutes
- Threshold: P95 < 200ms
- Result: See `events-list_20251224_122526.log`

### 3. Bookings List Test
- Script: `bookings-list-test.js`
- Target: 100 RPS sustained for 5 minutes
- Threshold: P95 < 200ms
- Result: See `bookings-list_20251224_122526.log`

## How to View Results

1. **Text logs:** Open `*_20251224_122526.log` files
2. **JSON data:** Open `*_20251224_122526.json` files
3. **Summary:** Open `*_20251224_122526_summary.json` files

## Next Steps

1. Review the logs for detailed metrics
2. Check Grafana dashboard at http://localhost:3001
3. Analyze Prometheus metrics at http://localhost:9090
4. Fill in the comprehensive report with actual numbers
