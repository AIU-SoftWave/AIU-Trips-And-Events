@echo off
REM AIU Trips & Events - Performance Test Suite Runner (Windows)
REM This script runs all performance tests and generates comprehensive reports

setlocal enabledelayedexpansion

REM Configuration
if "%BASE_URL%"=="" set BASE_URL=http://localhost:8080
set RESULTS_DIR=load-tests\results
set TIMESTAMP=%date:~10,4%%date:~4,2%%date:~7,2%_%time:~0,2%%time:~3,2%%time:~6,2%
set TIMESTAMP=%TIMESTAMP: =0%
set RUN_DIR=%RESULTS_DIR%\%TIMESTAMP%

REM Create results directory
if not exist "%RUN_DIR%" mkdir "%RUN_DIR%"

echo ========================================
echo AIU Trips ^& Events - Performance Tests
echo ========================================
echo.
echo Base URL: %BASE_URL%
echo Results Directory: %RUN_DIR%
echo Timestamp: %TIMESTAMP%
echo.

REM Check if k6 is installed
where k6 >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] k6 is not installed
    echo Please install k6 from: https://k6.io/docs/getting-started/installation/
    exit /b 1
)

REM Check if backend is running
echo Checking backend availability...
curl -f -s "%BASE_URL%/actuator/health" >nul 2>&1
if %errorlevel%==0 (
    echo [OK] Backend is available
    echo.
) else (
    echo [FAIL] Backend is not available at %BASE_URL%
    echo Please start the backend before running tests
    exit /b 1
)

REM Initialize counters
set passed=0
set failed=0

REM Test 1: Authentication (Login)
echo Running test: auth-login
echo -----------------------------------
k6 run --out json="%RUN_DIR%\auth-login_%TIMESTAMP%.json" --summary-export="%RUN_DIR%\auth-login_%TIMESTAMP%_summary.json" "load-tests\scripts\auth-login-test.js" > "%RUN_DIR%\auth-login_%TIMESTAMP%.log" 2>&1
if %errorlevel%==0 (
    echo [PASS] Test auth-login PASSED
    set /a passed+=1
) else (
    echo [FAIL] Test auth-login FAILED
    set /a failed+=1
)
echo.

REM Test 2: Events List
echo Running test: events-list
echo -----------------------------------
k6 run --out json="%RUN_DIR%\events-list_%TIMESTAMP%.json" --summary-export="%RUN_DIR%\events-list_%TIMESTAMP%_summary.json" "load-tests\scripts\events-list-test.js" > "%RUN_DIR%\events-list_%TIMESTAMP%.log" 2>&1
if %errorlevel%==0 (
    echo [PASS] Test events-list PASSED
    set /a passed+=1
) else (
    echo [FAIL] Test events-list FAILED
    set /a failed+=1
)
echo.

REM Test 3: Bookings List
echo Running test: bookings-list
echo -----------------------------------
k6 run --out json="%RUN_DIR%\bookings-list_%TIMESTAMP%.json" --summary-export="%RUN_DIR%\bookings-list_%TIMESTAMP%_summary.json" "load-tests\scripts\bookings-list-test.js" > "%RUN_DIR%\bookings-list_%TIMESTAMP%.log" 2>&1
if %errorlevel%==0 (
    echo [PASS] Test bookings-list PASSED
    set /a passed+=1
) else (
    echo [FAIL] Test bookings-list FAILED
    set /a failed+=1
)
echo.

REM Generate summary report
set /a total=passed+failed
echo ========================================
echo Test Summary
echo ========================================
echo Total Tests: %total%
echo Passed: %passed%
echo Failed: %failed%
echo.
echo Detailed results saved to: %RUN_DIR%
echo.

REM Create summary markdown
(
echo # Performance Test Summary
echo.
echo **Test Run:** %TIMESTAMP%
echo **Base URL:** %BASE_URL%
echo.
echo ## Results Overview
echo.
echo - **Total Tests:** %total%
echo - **Passed:** %passed%
echo - **Failed:** %failed%
echo.
echo ## Test Details
echo.
echo ### 1. Authentication ^(Login^) Test
echo - Script: `auth-login-test.js`
echo - Target: 100 RPS sustained for 5 minutes
echo - Threshold: P95 ^< 200ms
echo - Result: See `auth-login_%TIMESTAMP%.log`
echo.
echo ### 2. Events List Test
echo - Script: `events-list-test.js`
echo - Target: 100 RPS sustained for 5 minutes
echo - Threshold: P95 ^< 200ms
echo - Result: See `events-list_%TIMESTAMP%.log`
echo.
echo ### 3. Bookings List Test
echo - Script: `bookings-list-test.js`
echo - Target: 100 RPS sustained for 5 minutes
echo - Threshold: P95 ^< 200ms
echo - Result: See `bookings-list_%TIMESTAMP%.log`
echo.
echo ## How to View Results
echo.
echo 1. **Text logs:** Open `*_%TIMESTAMP%.log` files
echo 2. **JSON data:** Open `*_%TIMESTAMP%.json` files
echo 3. **Summary:** Open `*_%TIMESTAMP%_summary.json` files
echo.
echo ## Next Steps
echo.
echo 1. Review the logs for detailed metrics
echo 2. Check Grafana dashboard at http://localhost:3001
echo 3. Analyze Prometheus metrics at http://localhost:9090
echo 4. Fill in the comprehensive report with actual numbers
) > "%RUN_DIR%\SUMMARY.md"

echo [SUCCESS] Summary report created: %RUN_DIR%\SUMMARY.md
echo.

REM Exit with appropriate code
if %failed% gtr 0 (
    exit /b 1
) else (
    exit /b 0
)
