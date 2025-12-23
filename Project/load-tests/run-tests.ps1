# AIU Trips & Events - Performance Test Suite Runner (PowerShell)
# This script runs all performance tests and generates comprehensive reports

param(
    [string]$BaseUrl = "http://localhost:8080"
)

# Configuration
$ResultsDir = "load-tests\results"
$Timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$RunDir = "$ResultsDir\$Timestamp"

# Create results directory
if (-not (Test-Path $RunDir)) {
    New-Item -ItemType Directory -Path $RunDir -Force | Out-Null
}

Write-Host "========================================" -ForegroundColor Blue
Write-Host "AIU Trips & Events - Performance Tests" -ForegroundColor Blue
Write-Host "========================================" -ForegroundColor Blue
Write-Host ""
Write-Host "Base URL: " -NoNewline; Write-Host $BaseUrl -ForegroundColor Green
Write-Host "Results Directory: " -NoNewline; Write-Host $RunDir -ForegroundColor Green
Write-Host "Timestamp: " -NoNewline; Write-Host $Timestamp -ForegroundColor Green
Write-Host ""

# Function to run a single test
function Run-Test {
    param(
        [string]$TestName,
        [string]$TestScript
    )
    
    Write-Host "Running test: $TestName" -ForegroundColor Yellow
    Write-Host "-----------------------------------"
    
    $jsonOut = "$RunDir\${TestName}_${Timestamp}.json"
    $summaryOut = "$RunDir\${TestName}_${Timestamp}_summary.json"
    $logOut = "$RunDir\${TestName}_${Timestamp}.log"
    
    try {
        $output = & k6 run --out "json=$jsonOut" --summary-export="$summaryOut" $TestScript 2>&1
        $output | Out-File -FilePath $logOut -Encoding UTF8
        $exitCode = $LASTEXITCODE
        
        if ($exitCode -eq 0) {
            Write-Host "[PASS] Test $TestName PASSED" -ForegroundColor Green
            return $true
        } else {
            Write-Host "[FAIL] Test $TestName FAILED" -ForegroundColor Red
            return $false
        }
    } catch {
        Write-Host "[FAIL] Test $TestName FAILED" -ForegroundColor Red
        return $false
    } finally {
        Write-Host ""
    }
}

# Check if k6 is installed
try {
    $null = Get-Command k6 -ErrorAction Stop
} catch {
    Write-Host "[ERROR] k6 is not installed" -ForegroundColor Red
    Write-Host "Please install k6 from: https://k6.io/docs/getting-started/installation/"
    exit 1
}

# Check if backend is running
Write-Host "Checking backend availability..." -ForegroundColor Blue
try {
    $response = Invoke-WebRequest -Uri "$BaseUrl/actuator/health" -Method Get -TimeoutSec 5 -UseBasicParsing -ErrorAction Stop
    if ($response.StatusCode -eq 200) {
        Write-Host "[OK] Backend is available" -ForegroundColor Green
        Write-Host ""
    } else {
        Write-Host "[FAIL] Backend is not available at $BaseUrl" -ForegroundColor Red
        Write-Host "Please start the backend before running tests"
        exit 1
    }
} catch {
    Write-Host "[FAIL] Backend is not available at $BaseUrl" -ForegroundColor Red
    Write-Host "Please start the backend before running tests"
    exit 1
}

# Run all tests
$passed = 0
$failed = 0

# Test 1: Authentication (Login)
if (Run-Test -TestName "auth-login" -TestScript "load-tests\scripts\auth-login-test.js") {
    $passed++
} else {
    $failed++
}

# Test 2: Events List
if (Run-Test -TestName "events-list" -TestScript "load-tests\scripts\events-list-test.js") {
    $passed++
} else {
    $failed++
}

# Test 3: Bookings List
if (Run-Test -TestName "bookings-list" -TestScript "load-tests\scripts\bookings-list-test.js") {
    $passed++
} else {
    $failed++
}

# Generate summary report
$total = $passed + $failed
Write-Host "========================================" -ForegroundColor Blue
Write-Host "Test Summary" -ForegroundColor Blue
Write-Host "========================================" -ForegroundColor Blue
Write-Host "Total Tests: $total"
Write-Host "Passed: " -NoNewline; Write-Host $passed -ForegroundColor Green
Write-Host "Failed: " -NoNewline; Write-Host $failed -ForegroundColor Red
Write-Host ""
Write-Host "Detailed results saved to: " -NoNewline; Write-Host $RunDir -ForegroundColor Green
Write-Host ""

# Create summary markdown
$summaryContent = @"
# Performance Test Summary

**Test Run:** $Timestamp
**Base URL:** $BaseUrl

## Results Overview

- **Total Tests:** $total
- **Passed:** $passed
- **Failed:** $failed

## Test Details

### 1. Authentication (Login) Test
- Script: ``auth-login-test.js``
- Target: 100 RPS sustained for 5 minutes
- Threshold: P95 < 200ms
- Result: See ``auth-login_${Timestamp}.log``

### 2. Events List Test
- Script: ``events-list-test.js``
- Target: 100 RPS sustained for 5 minutes
- Threshold: P95 < 200ms
- Result: See ``events-list_${Timestamp}.log``

### 3. Bookings List Test
- Script: ``bookings-list-test.js``
- Target: 100 RPS sustained for 5 minutes
- Threshold: P95 < 200ms
- Result: See ``bookings-list_${Timestamp}.log``

## How to View Results

1. **Text logs:** Open ``*_${Timestamp}.log`` files
2. **JSON data:** Open ``*_${Timestamp}.json`` files
3. **Summary:** Open ``*_${Timestamp}_summary.json`` files

## Next Steps

1. Review the logs for detailed metrics
2. Check Grafana dashboard at http://localhost:3001
3. Analyze Prometheus metrics at http://localhost:9090
4. Fill in the comprehensive report with actual numbers
"@

$summaryContent | Out-File -FilePath "$RunDir\SUMMARY.md" -Encoding UTF8

Write-Host "[SUCCESS] Summary report created: $RunDir\SUMMARY.md" -ForegroundColor Green
Write-Host ""

# Exit with appropriate code
if ($failed -gt 0) {
    exit 1
} else {
    exit 0
}
