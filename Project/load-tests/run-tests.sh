#!/bin/bash

# AIU Trips & Events - Performance Test Suite Runner
# This script runs all performance tests and generates comprehensive reports

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
BASE_URL="${BASE_URL:-http://localhost:8080}"
RESULTS_DIR="./load-tests/results"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
RUN_DIR="${RESULTS_DIR}/${TIMESTAMP}"

# Create results directory
mkdir -p "${RUN_DIR}"

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}AIU Trips & Events - Performance Tests${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""
echo -e "Base URL: ${GREEN}${BASE_URL}${NC}"
echo -e "Results Directory: ${GREEN}${RUN_DIR}${NC}"
echo -e "Timestamp: ${GREEN}${TIMESTAMP}${NC}"
echo ""

# Function to run a single test
run_test() {
    local test_name=$1
    local test_script=$2
    
    echo -e "${YELLOW}Running test: ${test_name}${NC}"
    echo "-----------------------------------"
    
    k6 run \
        --out json="${RUN_DIR}/${test_name}_${TIMESTAMP}.json" \
        --summary-export="${RUN_DIR}/${test_name}_${TIMESTAMP}_summary.json" \
        "${test_script}" \
        | tee "${RUN_DIR}/${test_name}_${TIMESTAMP}.log"
    
    local exit_code=$?
    
    if [ $exit_code -eq 0 ]; then
        echo -e "${GREEN}✓ Test ${test_name} PASSED${NC}"
    else
        echo -e "${RED}✗ Test ${test_name} FAILED${NC}"
    fi
    
    echo ""
    return $exit_code
}

# Check if k6 is installed
if ! command -v k6 &> /dev/null; then
    echo -e "${RED}Error: k6 is not installed${NC}"
    echo "Please install k6 from: https://k6.io/docs/getting-started/installation/"
    exit 1
fi

# Check if backend is running
echo -e "${BLUE}Checking backend availability...${NC}"
if curl -f -s "${BASE_URL}/actuator/health" > /dev/null; then
    echo -e "${GREEN}✓ Backend is available${NC}"
    echo ""
else
    echo -e "${RED}✗ Backend is not available at ${BASE_URL}${NC}"
    echo "Please start the backend before running tests"
    exit 1
fi

# Run all tests
declare -i passed=0
declare -i failed=0

# Test 1: Authentication (Login)
if run_test "auth-login" "./load-tests/scripts/auth-login-test.js"; then
    ((passed++))
else
    ((failed++))
fi

# Test 2: Events List
if run_test "events-list" "./load-tests/scripts/events-list-test.js"; then
    ((passed++))
else
    ((failed++))
fi

# Test 3: Bookings List
if run_test "bookings-list" "./load-tests/scripts/bookings-list-test.js"; then
    ((passed++))
else
    ((failed++))
fi

# Generate summary report
echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}Test Summary${NC}"
echo -e "${BLUE}========================================${NC}"
echo -e "Total Tests: $((passed + failed))"
echo -e "${GREEN}Passed: ${passed}${NC}"
echo -e "${RED}Failed: ${failed}${NC}"
echo ""
echo -e "Detailed results saved to: ${GREEN}${RUN_DIR}${NC}"
echo ""

# Create summary markdown
cat > "${RUN_DIR}/SUMMARY.md" << EOF
# Performance Test Summary

**Test Run:** ${TIMESTAMP}
**Base URL:** ${BASE_URL}

## Results Overview

- **Total Tests:** $((passed + failed))
- **Passed:** ${passed}
- **Failed:** ${failed}

## Test Details

### 1. Authentication (Login) Test
- Script: \`auth-login-test.js\`
- Target: 100 RPS sustained for 5 minutes
- Threshold: P95 < 200ms
- Result: See \`auth-login_${TIMESTAMP}.log\`

### 2. Events List Test
- Script: \`events-list-test.js\`
- Target: 100 RPS sustained for 5 minutes
- Threshold: P95 < 200ms
- Result: See \`events-list_${TIMESTAMP}.log\`

### 3. Bookings List Test
- Script: \`bookings-list-test.js\`
- Target: 100 RPS sustained for 5 minutes
- Threshold: P95 < 200ms
- Result: See \`bookings-list_${TIMESTAMP}.log\`

## How to View Results

1. **Text logs:** Open \`*_${TIMESTAMP}.log\` files
2. **JSON data:** Open \`*_${TIMESTAMP}.json\` files
3. **Summary:** Open \`*_${TIMESTAMP}_summary.json\` files

## Next Steps

1. Review the logs for detailed metrics
2. Check Grafana dashboard at http://localhost:3001
3. Analyze Prometheus metrics at http://localhost:9090
4. Fill in the comprehensive report with actual numbers

EOF

echo -e "${GREEN}Summary report created: ${RUN_DIR}/SUMMARY.md${NC}"
echo ""

# Exit with appropriate code
if [ $failed -gt 0 ]; then
    exit 1
else
    exit 0
fi
