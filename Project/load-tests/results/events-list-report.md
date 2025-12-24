# Events List Performance Test Report

**Component Tested:** Events List API (`GET /api/events`)
**Test Date:** 2025-12-24
**Tested By:** Copilot (requested by user)
**Target SLO:** P95 < 200 ms @ 100 RPS; error rate < 5%

## Test Configuration

- Tool: k6
- Script: `scripts/events-list-test.js`
- Load pattern: 30s -> 50 RPS, 30s -> 100 RPS, 5m sustain @ 100 RPS, 30s ramp-down (total ~6.5m)
- Base URL: http://localhost:8080
- Auth: JWT from setup step inside the script

## Results (k6 run)

- Total requests: 34,393
- Throughput: ~88.0 RPS (below 100 target due to high failures)
- Success rate: **1.15%** (395/34,393)
- Error rate: **98.85%** (mostly non-200 responses)
- Latency (all responses):
  - P50: 3.57 ms
  - P90: 4.60 ms
  - P95: 5.22 ms
  - P99: 7.47 ms
  - Avg: 3.72 ms
  - Min: 1.53 ms
  - Max: 64.87 ms

## SLO Evaluation

- P95 < 200 ms: ✅ (5.22 ms)
- Error rate < 5%: ❌ (98.85%)
- Sustained 100 RPS: ❌ (achieved ~88 RPS due to failures)
- Overall status: **FAIL** (blocked by high failure rate)

## Observations / Likely Cause

- Only ~1% of requests returned 200. The script did obtain a JWT in setup, so failures are likely authorization (401/403) or another non-200 response from the events endpoint. Need to check backend logs or run a quick single request with the same token to confirm.

## Next Steps

1. Inspect a few sample responses to confirm status code/body (run: `k6 run --http-debug=full scripts/events-list-test.js --vus 1 --duration 10s`).
2. Verify that `GET /api/events` is actually public (SecurityConfig) and that any auth checks are satisfied with the token issued to `admin@aiu.edu`.
3. Re-run the load after fixing the error condition; expect success rate ~100% and throughput ~100 RPS while keeping P95 < 200 ms.

## Evidence Files

- Summary JSON: `results/events-list_summary.json`
- Raw metrics: `results/events-list.json`
- k6 console output: run executed from `Project/load-tests`.
